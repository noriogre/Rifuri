package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import forms.DailyForm;
import forms.MonthlyForm;
import models.M_Service;
import models.ServiceNameSearch;
import models.UsageHistoryDailyFields;
import models.UsageHistoryDailySearch;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.WEB_T_000;
import views.html.WEB_U_001;

public class UsageHistoryDailyDisplay extends Controller {

	@Inject FormFactory formFactory;

    public Result showUsageHistoryDaily() {

		/* 利用履歴の取得結果を格納するListを準備する。 ----------------------------------------- */
		List<UsageHistoryDailyFields> usageHistoryResult = new ArrayList<UsageHistoryDailyFields>();

    	/* フォームに入力された検索条件（年月日）を取得する。 ----------------------------------------- */
//		DynamicForm input = formFactory.form().bindFromRequest();
//		String searchDate = input.data().get("search_date");
		Form<DailyForm> dailyForm = formFactory.form(DailyForm.class).bindFromRequest();
		DailyForm input = null;
		if (dailyForm.hasErrors()) {
			Form<MonthlyForm> monthlyForm = formFactory.form(MonthlyForm.class);
		    return badRequest(WEB_T_000.render(dailyForm, monthlyForm));
		} else {
			input = dailyForm.get();
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String searchDate = dateFormat.format(input.getTargetDate());


		/* 利用履歴一覧表のヘッダーを準備する。 ----------------------------------------- */
		ServiceNameSearch serviceNameSearch = new ServiceNameSearch();
		List<M_Service> serviceNameList = serviceNameSearch.searchServiceName();


		/* 1日分の利用履歴を取得する。 ----------------------------------------- */
		UsageHistoryDailySearch usageHistoryDailySearch = new UsageHistoryDailySearch();
		UsageHistoryDailySearch searchUsageHistoryDailyResults = usageHistoryDailySearch.searchUsageHistoryDaily(searchDate, serviceNameList);

		if (searchUsageHistoryDailyResults.getErrMsg() == null) {
			usageHistoryResult = searchUsageHistoryDailyResults.getUsageHistoryResult();
		}else{
			return badRequest(searchUsageHistoryDailyResults.getErrMsg());
		}

		return ok(WEB_U_001.render(searchDate,serviceNameList,usageHistoryResult));
    }
}
