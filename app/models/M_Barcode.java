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
 * バーコードマスタエンティティクラス
 *
 */

@Entity
@Table(name = "m_barcode")
public class M_Barcode extends Model {

	/**
	 * バーコードID
	 */
	@Id
	@Column(name = "barcode_id")
	public Integer barcodeId;

	/**
	 * バーコード
	 */
	@Column(name = "barcode")
	public String barcode;

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


    public static Finder<Long, M_Barcode> find =
            new Finder<Long, M_Barcode>(Long.class, M_Barcode.class);

}
