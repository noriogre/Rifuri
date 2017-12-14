package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import models.CustomerNumChecker;
import models.UsageHistoryMonthlyFields;
import models.UsageHistoryMonthlySearch;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.WEB_U_002;

public class UsageHistoryMonthlyDisplay extends Controller {

	@Inject FormFactory formFactory;

    public Result showUsageHistoryMonthly() {

		/* バイタル情報（1日毎）の取得結果を格納するための変数を準備する。 ----------------------------------------- */
    	List<UsageHistoryMonthlyFields> usageHistoryMonthlyResult = new ArrayList<UsageHistoryMonthlyFields>();


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


		/* 1ヶ月分の利用履歴をDBより取得する。----------------------------------------- */
		UsageHistoryMonthlySearch usageHistoryMonthlySearch = new UsageHistoryMonthlySearch();
		UsageHistoryMonthlySearch searchUsageHistoryMonthlyResults = usageHistoryMonthlySearch.searchUsageHistoryMonthly(searchMonth, searchCustomerName, searchCustomerNumber);

		if (searchUsageHistoryMonthlyResults.getErrMsg() == null) {
			usageHistoryMonthlyResult = searchUsageHistoryMonthlyResults.getUsageHistoryMonthlyResult();
		}else{
			return badRequest(searchUsageHistoryMonthlyResults.getErrMsg());
		}


		return ok(WEB_U_002.render(searchMonth,searchCustomerName,searchCustomerNumber,usageHistoryMonthlyResult));
    }
}
