package controllers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlUpdate;
import com.fasterxml.jackson.databind.JsonNode;

import play.mvc.Controller;
import play.mvc.Result;

public class InsertDataFromAndroid extends Controller {

	Integer customer_id;
	Integer service_id;
	boolean is_deleted;
	Timestamp created_at;
	Timestamp updated_at;

	public Result InsertData() {

		JsonNode json = request().body().asJson();

		try {
			customer_id = Integer.parseInt(json.findPath("customer_id").textValue());
			service_id = Integer.parseInt(json.findPath("service_id").textValue());
			is_deleted = Boolean.valueOf(json.findPath("is_deleted").textValue());
			created_at = new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(json.findPath("created_at").textValue()).getTime());
			updated_at = new Timestamp(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(json.findPath("updated_at").textValue()).getTime());
		} catch (Exception e) {
			e.printStackTrace();
			return badRequest("Bad Json data");
		}

		String insertSql = "INSERT INTO t_usage_history VALUES (:cid,:sid,:del,:ctime,:utime)";
		SqlUpdate insert = Ebean.createSqlUpdate(insertSql);
		insert.setParameter("cid", customer_id);
		insert.setParameter("sid", service_id);
		insert.setParameter("del", is_deleted);
		insert.setParameter("ctime", created_at);
		insert.setParameter("utime", updated_at);

		insert.execute();

		return ok("Insert Complete");
	}
}
