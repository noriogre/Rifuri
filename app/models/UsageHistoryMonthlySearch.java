package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import lombok.Getter;

public class UsageHistoryMonthlySearch {

	/* バイタル情報（1日毎）の取得結果を格納するための変数を準備する。 ----------------------------------------- */
	@Getter
	private List<UsageHistoryMonthlyFields> usageHistoryMonthlyResult = new ArrayList<UsageHistoryMonthlyFields>();

	@Getter
	private String errMsg = null;


	public UsageHistoryMonthlySearch searchUsageHistoryMonthly(String searchMonth, String searchCustomerName, String searchCustomerNumber) {


		UsageHistoryMonthlySearch searchResults = new UsageHistoryMonthlySearch();


		/* --- 1日毎のバイタル情報をDBより取得する。（閲覧年月、利用者名を使用して取得） ----------------------------------------- */
		String usageHistorySql = "select case ms.service_div when '1' then '介護' when '2' then '利用者' end as service_div"
				+" ,ms.service_name"
				+" ,string_agg(distinct to_char(tu.created_at, 'YYYY-MM-DD'), ',' order by to_char(tu.created_at, 'YYYY-MM-DD')) as created_at "
				+"from t_usage_history tu "
				+"inner join m_service ms "
				+"on tu.service_id = ms.service_id "
				+"inner join m_customer mc "
				+"on tu.customer_id = mc.customer_id "
				+"where to_char(tu.created_at, 'YYYY-MM') = :targetMonth "
				+"and mc.customer_name = :targetCustomerName "
				+"and mc.customer_number = :targetCustomerNum "
				+"and mc.is_deleted = false "
				+"and ms.is_deleted = false "
				+"group by ms.service_div, ms.service_id, ms.service_name "
				+"order by ms.service_div, ms.service_id";

		SqlQuery usageHistorySqlQuery = Ebean.createSqlQuery(usageHistorySql)
				.setParameter("targetMonth", searchMonth)
				.setParameter("targetCustomerName", searchCustomerName)
				.setParameter("targetCustomerNum", searchCustomerNumber);

		List<SqlRow> usageHistoryList = usageHistorySqlQuery.findList();


		/* --- 取得した結果リストを該当テーブルのモデルクラスにマッピングする。 ----------------------------------------- */
		/* 指定した年月の日数を取得する。 ----------------------------------------- */
		int year = Integer.parseInt(searchMonth.substring(0,4));
		int month = Integer.parseInt(searchMonth.substring(5,7));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		int lastDayOfMonth = cal.getActualMaximum(Calendar.DATE);

		if (usageHistoryList != null && usageHistoryList.size() > 0) {
			for (SqlRow row: usageHistoryList) {
				UsageHistoryMonthlyFields fields = new UsageHistoryMonthlyFields();
				fields.serviceDiv = row.getString("service_div");
				fields.serviceName = row.getString("service_name");
				fields.createdAt = row.getString("created_at");

				if (fields.createdAt != null) {
					String[] createdAtArray = fields.createdAt.split(",", 0);
					for (int date = 1; date <= lastDayOfMonth; date++) {
						if (Arrays.asList(createdAtArray).contains(searchMonth + "-" + String.format("%02d", date))) {
							fields.createdAtList.add("1");
						}else {
							fields.createdAtList.add("");
						}
					}
				searchResults.usageHistoryMonthlyResult.add(fields);
				}
			}
		}else{
			searchResults.errMsg = "検索結果が0件です。検索条件を変更してください。";
			return searchResults;
		}


		return searchResults;
	}
}
