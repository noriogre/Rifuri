package models;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import lombok.Getter;

public class CustomerNumChecker {

	@Getter
	private String customerNum = null;

	@Getter
	private String errMsg = null;

	public CustomerNumChecker checkCustomerNum(String searchCustomerName) {

		CustomerNumChecker checkResults = new CustomerNumChecker();

		/* 利用者名を検索条件に、被保険者番号の情報をDBより取得する。 ----------------------------------------- */
		String customerNumberSql = "select customer_number "
				+"from m_customer "
				+"where customer_name = :targetCustomerName";

		SqlQuery customerNumberRawSql = Ebean.createSqlQuery(customerNumberSql)
				.setParameter("targetCustomerName", searchCustomerName);

		List<SqlRow> customerNumberList = customerNumberRawSql.findList();

		/* 取得した被保険者番号の件数により、処理を分岐する。 ----------------------------------------- */
		if (customerNumberList.size() == 0) {
			checkResults.errMsg = "該当する利用者が存在しません。検索条件を変更してください。";
		} else if (customerNumberList.size() > 1) {
			checkResults.errMsg = "同姓同名の利用者が複数人存在します。被保険者番号を指定してください。";
		} else {
			checkResults.customerNum = customerNumberList.get(0).getString("customer_number");
		}

		return checkResults;
	}
}
