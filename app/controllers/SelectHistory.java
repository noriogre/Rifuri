package controllers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.avaje.ebean.ExpressionList;

import models.T_Usage_History;
import play.data.DynamicForm;
import play.data.FormFactory;
//import models.T_Usage_History;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.history_list;

@Singleton
public class SelectHistory extends Controller {

	@Inject FormFactory formFactory;

	public Result selectHistory() {

//		List<T_Usage_History> T_Usage_HistoryList = T_Usage_History.getFind().all();

//		String sql = "SELECT * FROM T_USAGE_HISTORY W";

//		List<SqlRow> sqlRows = Ebean.createSqlQuery(sql).findList();


	    DynamicForm input = formFactory.form().bindFromRequest();
	    Integer id = Integer.parseInt(input.get("customer_id"));

/*	    if (id == null) {
	        return badRequest("IDを入力してください。");0
	    }
*/
		ExpressionList<T_Usage_History> datalist = T_Usage_History.find.where().eq("customer_id",id);
		List<T_Usage_History> datas = datalist .findList();

		return ok(history_list.render(datas));
	}
}
