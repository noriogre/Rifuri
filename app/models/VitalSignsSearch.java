package models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import lombok.Getter;

public class VitalSignsSearch extends VitalSignsFields {

	/* バイタル情報（1日毎）の取得結果を格納するための変数を準備する。 ----------------------------------------- */
	@Getter
	private VitalSignsFields vitalSignsResult = new VitalSignsFields();

	@Getter
	private String errMsg = null;


	public VitalSignsSearch searchVitalSings(String searchMonth, String searchCustomerName, String searchCustomerNumber) {


		VitalSignsSearch searchResults = new VitalSignsSearch();


		/* --- 1日毎のバイタル情報をDBより取得する。（閲覧年月、利用者名を使用して取得） ----------------------------------------- */
		String vitalSigsSQL = "select tv.body_temperature, tv.low_blood_pressure, tv.high_blood_pressure, tv.heart_rate, tv.coma_scale, tv.respiration_rate, to_char(tv.created_at, 'YYYY-MM-DD') created_at "
				+"from t_vital_signs tv "
				+"inner join m_customer mc "
				+"on tv.customer_id = mc.customer_id "
				+"where to_char(tv.created_at, 'YYYY-MM') = :targetMonth "
				+"and mc.customer_name = :targetCustomerName "
				+"and mc.customer_number = :targetCustomerNum "
				+"and mc.is_deleted = false "
				+"order by tv.created_at";

		SqlQuery vitalSignsSQLQuery = Ebean.createSqlQuery(vitalSigsSQL)
				.setParameter("targetMonth", searchMonth)
				.setParameter("targetCustomerName", searchCustomerName)
				.setParameter("targetCustomerNum", searchCustomerNumber);

		List<SqlRow> vitalSignsList = vitalSignsSQLQuery.findList();


		/* --- 取得した結果リストを該当テーブルのモデルクラスにマッピングする。 ----------------------------------------- */
		List<VitalSignsFields> vitalSignsResultTemp = new ArrayList<VitalSignsFields>();

		if (vitalSignsList != null && vitalSignsList.size() > 0) {
			for (SqlRow row: vitalSignsList) {
				VitalSignsFields fields = new VitalSignsFields();
				fields.bodyTemperature = row.getString("body_temperature");
				fields.lowBloodPressure = row.getString("low_blood_pressure");
				fields.highBloodPressure = row.getString("high_blood_pressure");
				fields.heartRate = row.getString("heart_rate");
				fields.comaScale = row.getString("coma_scale");
				fields.respirationRate = row.getString("respiration_rate");
				fields.createdAt = row.getString("created_at");

				vitalSignsResultTemp.add(fields);
			}
		}else{
			searchResults.errMsg = "検索結果が0件です。検索条件を変更してください。";
			return searchResults;
		}


		/* 画面WEB_V_003への連携データを作成する。 ----------------------------------------- */
		/* 指定した年月の日数を取得する。 ----------------------------------------- */
		int year = Integer.parseInt(searchMonth.substring(0,4));
		int month = Integer.parseInt(searchMonth.substring(5,7));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		int lastDayOfMonth = cal.getActualMaximum(Calendar.DATE);

		/* 1日毎のバイタル情報をListに格納する。 ----------------------------------------- */
		boolean isTargetDate = false;
		for (int date = 1; date <= lastDayOfMonth; date++) {
			for (VitalSignsFields listRow: vitalSignsResultTemp) {
				if (listRow.getCreatedAt().equals(searchMonth + "-" + String.format("%02d", date))) {
					searchResults.vitalSignsResult.bodyTemperatureList.add(listRow.getBodyTemperature());
					searchResults.vitalSignsResult.lowBloodPressureList.add(listRow.getLowBloodPressure());
					searchResults.vitalSignsResult.highBloodPressureList.add(listRow.getHighBloodPressure());
					searchResults.vitalSignsResult.heartRateList.add(listRow.getHeartRate());
					searchResults.vitalSignsResult.comaScaleList.add(listRow.getComaScale());
					searchResults.vitalSignsResult.respirationRateList.add(listRow.getRespirationRate());
					isTargetDate = true;
				}
			}
			if (isTargetDate == false) {
				searchResults.vitalSignsResult.bodyTemperatureList.add("");
				searchResults.vitalSignsResult.lowBloodPressureList.add("");
				searchResults.vitalSignsResult.highBloodPressureList.add("");
				searchResults.vitalSignsResult.heartRateList.add("");
				searchResults.vitalSignsResult.comaScaleList.add("");
				searchResults.vitalSignsResult.respirationRateList.add("");
			}
			isTargetDate = false;
		}

		return searchResults;
	}
}
