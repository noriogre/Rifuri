package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.CustomerNumChecker;
import models.VitalSignAggregateFields;
import models.VitalSignAggregateSearch;
import models.VitalSignsFields;
import models.VitalSignsSearch;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.WEB_V_003;

public class VitalSignsDisplay extends Controller {

	@Inject FormFactory formFactory;

    public Result showVitalSigns() {

		/* バイタル情報（1日毎）の取得結果を格納するための変数を準備する。 ----------------------------------------- */
		VitalSignsFields vitalSignsResult;

		/* バイタル情報（1ヶ月分集約）の取得結果を格納するためのListを準備する。 ----------------------------------------- */
		List<VitalSignAggregateFields> vitalSignAggregateResult = new ArrayList<VitalSignAggregateFields>();


    	/* フォームに入力された検索条件を取得する。----------------------------------------- */
		DynamicForm input = formFactory.form().bindFromRequest();
		String searchMonth = input.data().get("search_month");
		String searchCustomerName = input.data().get("search_customer_name");
		String searchCustomerNumber = input.data().get("search_customer_number");


		/* 被保険者番号が入力されなかった場合に実行する。
		 * 指定された利用者が存在し、かつ複数人存在しないことを確認する。 ----------------------------------------- */
		if (searchCustomerNumber == null || searchCustomerNumber == "") {
			CustomerNumChecker customerNumChecker = new CustomerNumChecker();
			CustomerNumChecker checkCustomerNumResults = customerNumChecker.checkCustomerNum(searchCustomerName);

			if (checkCustomerNumResults.getCustomerNum() != null) {
				searchCustomerNumber = checkCustomerNumResults.getCustomerNum();
			}else{
				return badRequest(checkCustomerNumResults.getErrMsg());
			}
		}


		/* 1日毎のバイタル情報をDBより取得する。 ----------------------------------------- */
		VitalSignsSearch vitalSignsSearch = new VitalSignsSearch();
		VitalSignsSearch searchVitalSignsResults = vitalSignsSearch.searchVitalSings(searchMonth, searchCustomerName, searchCustomerNumber);

		if (searchVitalSignsResults.getErrMsg() == null) {
			vitalSignsResult = searchVitalSignsResults.getVitalSignsResult();
		}else{
			return badRequest(searchVitalSignsResults.getErrMsg());
		}


		/* 1ヶ月分のバイタル情報を集約しDBより取得する。（閲覧年月、利用者名を使用して取得） ----------------------------------------- */
		VitalSignAggregateSearch vitalSignAggregateSearch = new VitalSignAggregateSearch();
		VitalSignAggregateSearch searchVitalSignAggregateResults = vitalSignAggregateSearch.searchVitalSignAggregate(searchMonth, searchCustomerName, searchCustomerNumber);

		if (searchVitalSignAggregateResults.getErrMsg() == null) {
			vitalSignAggregateResult = searchVitalSignAggregateResults.getVitalSignAggregateResult();
		}else{
			return badRequest(searchVitalSignAggregateResults.getErrMsg());
		}


        return ok(WEB_V_003.render(searchMonth,searchCustomerName,searchCustomerNumber,vitalSignsResult,vitalSignAggregateResult));
    }
}
