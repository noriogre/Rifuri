package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;

/**
 *
 * 店舗マスタエンティティクラス
 *
 */

@Entity
@Table(name = "m_store")
public class M_Store extends Model {

	/**
	 * 店舗ID
	 */
	@Id
	@Column(name = "store_id")
	public Integer storeId;

	/**
	 * 店舗名
	 */
	@Column(name = "store_name")
	public String storeName;

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


    public static Finder<Long, M_Store> find =
            new Finder<Long, M_Store>(Long.class, M_Store.class);

}
