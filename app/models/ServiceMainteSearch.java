package models;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import lombok.Getter;

public class ServiceMainteSearch {

	/* サービス／バーコード情報の取得結果を格納するための変数を準備する。 ----------------------------------------- */
	@Getter
	private List<ServiceMainteFields> serviceMainteResult = new ArrayList<ServiceMainteFields>();

	@Getter
	private String errMsg = null;


	public ServiceMainteSearch searchServiceMainte() {


		ServiceMainteSearch searchResults = new ServiceMainteSearch();


		/* --- サービス情報をDBより取得する。 ----------------------------------------- */
		String serviceMainteSql = "select ms.service_name"
				+" ,case ms.service_div when '1' then '介護' when '2' then '利用者' end as service_div"
				+" ,mb.barcode "
				+"from m_service ms "
				+"inner join m_barcode mb "
				+"on ms.barcode_id = mb.barcode_id "
				+"where ms.is_deleted = false "
				+"order by ms.service_div, service_id";

		SqlQuery serviceMainteSqlQuery = Ebean.createSqlQuery(serviceMainteSql);

		List<SqlRow> serviceMainteList = serviceMainteSqlQuery.findList();


		/* --- 取得した結果リストをモデルクラスにマッピングする。 ----------------------------------------- */

		if (serviceMainteList != null && serviceMainteList.size() > 0) {
			for (SqlRow row: serviceMainteList) {
				ServiceMainteFields fields = new ServiceMainteFields();
				fields.serviceName = row.getString("service_name");
				fields.serviceDiv = row.getString("service_div");
				fields.barcode = row.getString("barcode");

				searchResults.serviceMainteResult.add(fields);
			}
		}else{
			searchResults.errMsg = "検索結果が0件です。管理者に問い合わせを行ってください。";
			return searchResults;
		}


		return searchResults;
	}
}
