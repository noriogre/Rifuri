package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import lombok.Getter;

public class VitalSignAggregateSearch {

	/* バイタル情報（1ヶ月分集約）の取得結果を格納するためのListを準備する。 ----------------------------------------- */
	@Getter
	private List<VitalSignAggregateFields> vitalSignAggregateResult = new ArrayList<VitalSignAggregateFields>();

	@Getter
	private String errMsg = null;


	public VitalSignAggregateSearch searchVitalSignAggregate(String searchMonth, String searchCustomerName, String searchCustomerNumber) {

		VitalSignAggregateSearch searchResults = new VitalSignAggregateSearch();

		/* 1ヶ月分のバイタル情報を集約しDBより取得する。（閲覧年月、利用者名を使用して取得） ----------------------------------------- */
		String vitalSignAggregateSQL = "select mc.customer_name"
				+" ,min(tv.body_temperature) as lowest_body_temperature"
				+" ,max(tv.body_temperature) as highest_body_temperature"
				+" ,avg(tv.body_temperature) as average_body_temperature"
				+" ,min(tv.low_blood_pressure) as lowest_blood_pressure"
				+" ,max(tv.high_blood_pressure) as highest_blood_pressure"
				+" ,sum(tv.low_blood_pressure+tv.high_blood_pressure)/(count(tv.low_blood_pressure)+count(tv.high_blood_pressure)) as average_blood_pressure "
				+"from t_vital_signs tv "
				+"inner join m_customer mc "
				+"on tv.customer_id = mc.customer_id "
				+"where to_char(tv.created_at, 'YYYY-MM') = :targetMonth "
				+"and mc.customer_name = :targetCustomerName "
				+"and mc.customer_number = :targetCustomerNum "
				+"and mc.is_deleted = false "
				+"group by mc.customer_name";

		SqlQuery vitalSignAggregateSqlQuery = Ebean.createSqlQuery(vitalSignAggregateSQL)
				.setParameter("targetMonth", searchMonth)
				.setParameter("targetCustomerName", searchCustomerName)
				.setParameter("targetCustomerNum", searchCustomerNumber);

		List<SqlRow> vitalSignAggregateList = vitalSignAggregateSqlQuery.findList();

		if (vitalSignAggregateList != null && vitalSignAggregateList.size() > 0) {
			for (SqlRow row: vitalSignAggregateList) {
				VitalSignAggregateFields fields = new VitalSignAggregateFields();
				fields.lowestBodyTemperature = row.getDouble("lowest_body_temperature");
				fields.highestBodyTemperature = row.getDouble("highest_body_temperature");
				fields.averageBodyTemperature = new BigDecimal(String.valueOf(row.getDouble("average_body_temperature"))).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
				fields.lowestBloodPressure = row.getInteger("lowest_blood_pressure");
				fields.highestBloodPressure = row.getInteger("highest_blood_pressure");
				fields.averageBloodPressure = row.getInteger("average_blood_pressure");

				searchResults.vitalSignAggregateResult.add(fields);
			}
		}else{
			searchResults.errMsg = "検索結果が0件です。検索年月あるいは利用者を変更してください。";
			return searchResults;
		}


		return searchResults;
	}
}
