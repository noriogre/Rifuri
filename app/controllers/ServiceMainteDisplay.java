package controllers;

import java.util.ArrayList;
import java.util.List;

import models.ServiceMainteFields;
import models.ServiceMainteSearch;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.WEB_M_004;

public class ServiceMainteDisplay extends Controller {

    public Result showServiceMainte() {

		/* 利用履歴の取得結果を格納するListを準備する。 ----------------------------------------- */
		List<ServiceMainteFields> serviceMainteResult = new ArrayList<ServiceMainteFields>();


		/* 利用履歴一覧表のヘッダーを準備する。 ----------------------------------------- */
		ServiceMainteSearch serviceMainteSearch = new ServiceMainteSearch();
		ServiceMainteSearch searchServiceMainteResults = serviceMainteSearch.searchServiceMainte();

		if (searchServiceMainteResults.getErrMsg() == null) {
			serviceMainteResult = searchServiceMainteResults.getServiceMainteResult();
		}else{
			return badRequest(searchServiceMainteResults.getErrMsg());
		}

        return ok(WEB_M_004.render(serviceMainteResult));
    }

}
