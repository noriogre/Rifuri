package models;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

public class ServiceNameSearch {

	public List<M_Service> searchServiceName() {

		/* サービス名をDBから取得する。（利用履歴一覧表のヘッダーを準備する。） ----------------------------------------- */
		String serviceNameSql = "select service_name "
				+"from m_service "
				+"where is_deleted = false "
				+"order by service_div, service_id";

		RawSql serviceNameRawSql = RawSqlBuilder.parse(serviceNameSql)
				.columnMapping("service_name", "serviceName")
				.create();

		List<M_Service> serviceNameList = Ebean.find(M_Service.class)
				.setRawSql(serviceNameRawSql)
				.findList();

		return serviceNameList;
	}
}
