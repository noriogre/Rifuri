package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;

/**
 *
 * 利用履歴エンティティクラス
 *
 */

@Entity
@Table(name = "t_usage_history")
public class T_Usage_History extends Model {

	/**
	 * 利用者ID
	 */
	@Column(name = "customer_id")
	public Integer customerId;

	/**
	 * サービスID
	 */
	@Column(name = "service_id")
	public Integer serviceId;

	/**
	 * 削除フラグ
	 */
	@Column(name = "is_deleted")
	public boolean isDeleted;

	/**
	 * 登録日時
	 */
	@CreatedTimestamp
	@Column(name = "created_at")
	public Timestamp createdAt;

	/**
	 * 更新日時
	 */
	@CreatedTimestamp
	@Column(name = "updated_at")
	public Timestamp updatedAt;


    public static Finder<Long, T_Usage_History> find =
            new Finder<Long, T_Usage_History>(Long.class, T_Usage_History.class);

}
