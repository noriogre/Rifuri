package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import lombok.Getter;

public class UsageHistoryDailySearch {

	/* 利用履歴の取得結果を格納するListを準備する。 ----------------------------------------- */
	@Getter
	private List<UsageHistoryDailyFields> usageHistoryResult = new ArrayList<UsageHistoryDailyFields>();

	@Getter
	private String errMsg = null;

	public UsageHistoryDailySearch searchUsageHistoryDaily(String searchDate, List<M_Service> serviceNameList) {

		UsageHistoryDailySearch searchResults = new UsageHistoryDailySearch();

		/* 1日分の利用履歴を取得する。 ----------------------------------------- */
		String usageHistorySql = "select mc.customer_name, t3.body_temperature, t3.low_blood_pressure, t3.high_blood_pressure, t3.heart_rate, t2.service_name "
				+"from (select t1.customer_id ,string_agg(t1.service_name, ',' order by t1.service_div, t1.service_id) as service_name "
				+"from (select tu.customer_id ,tu.service_id ,ms.service_div ,ms.service_name "
				+"from t_usage_history tu "
				+"inner join m_service ms "
				+"on tu.service_id = ms.service_id "
				+"where ms.is_deleted = false "
				+"and to_char(tu.created_at, 'YYYY-MM-DD') = :targetDate "
				+"group by ms.service_name, ms.service_div, tu.service_id, tu.customer_id "
				+"order by ms.service_div, tu.service_id) t1 "
				+"group by t1.customer_id "
				+"order by t1.customer_id) t2 "
				+"inner join (select tv.customer_id, tv.body_temperature, tv.low_blood_pressure, tv.high_blood_pressure, tv.heart_rate "
				+"from t_vital_signs tv "
				+"where to_char(tv.created_at, 'YYYY-MM-DD') = :targetDate) t3 "
				+"on t2.customer_id = t3.customer_id "
				+"inner join m_customer mc "
				+"on t3.customer_id = mc.customer_id "
				+"order by mc.customer_name;";

		SqlQuery usageHistorySqlQuery = Ebean.createSqlQuery(usageHistorySql)
				.setParameter("targetDate", searchDate);

		List<SqlRow> usageHistoryList = usageHistorySqlQuery.findList();

		/* 取得した利用履歴の結果を該当テーブルのモデルクラスにマッピングする。 ----------------------------------------- */
		if (usageHistoryList != null && usageHistoryList.size() > 0) {
			for (SqlRow row: usageHistoryList) {
				UsageHistoryDailyFields fields = new UsageHistoryDailyFields();
				fields.customerName = row.getString("customer_name");
				fields.bodyTemperature = row.getDouble("body_temperature");
				fields.lowBloodPressure = row.getInteger("low_blood_pressure");
				fields.highBloodPressure = row.getInteger("high_blood_pressure");
				fields.heartRate = row.getInteger("heart_rate");
				fields.serviceNames = row.getString("service_name");

				if (fields.serviceNames != null) {
					String[] usageServiceArray = fields.serviceNames.split(",", 0);
					for (int i = 0; i < serviceNameList.size(); i++) {
						if (Arrays.asList(usageServiceArray).contains(serviceNameList.get(i).serviceName)) {
							fields.serviceName.add("●");
						}else {
							fields.serviceName.add("");
						}
					}
				}
				searchResults.usageHistoryResult.add(fields);
			}
		}else{
			searchResults.errMsg = "検索結果が0件です。検索条件を変更してください。";
			return searchResults;
		}

		return searchResults;
	}
}
