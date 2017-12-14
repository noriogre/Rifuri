package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.annotation.Sql;

/**
 *
 * サービスマスタエンティティクラス
 *
 */

@Entity
@Sql
@Table(name = "m_service")
public class M_Service extends Model {

	/**
	 * サービスID
	 */
	@Id
	@Column(name = "service_id")
	public Integer serviceId;

	/**
	 * 店舗ID
	 */
	@Column(name = "store_id")
	public Integer storeId;

	/**
	 * バーコードID
	 */
	@Column(name = "barcode_id")
	public Integer barcodeId;

	/**
	 * サービス名
	 */
	@Column(name = "service_name")
	public String serviceName;

	/**
	 * サービス区分
	 */
	@Column(name = "service_div")
	public String serviceDiv;

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


    public static Finder<Integer, M_Service> find =
            new Finder<Integer, M_Service>(Integer.class, M_Service.class);

}
