package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;

/**
 *
 * 利用者マスタエンティティクラス
 *
 */

@Entity
@Table(name = "m_customer")
public class M_Customer extends Model {

	/**
	 * 利用者ID
	 */
	@Id
	@Column(name = "customer_id")
	public Integer customerId;

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
	 * 介護保険被保険者証番号
	 */
	@Column(name = "customer_number")
	public String customerNumber;

	/**
	 * 氏名
	 */
	@Column(name = "customer_name")
	public String customerName;

	/**
	 * 生年月日
	 */
	@Column(name = "birthday")
	public Date Birthday;

	/**
	 * 性別
	 */
	@Column(name = "gender_div")
	public String genderDiv;

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    public List<T_Vital_Signs> vitalSigns = new ArrayList<>();


    public static Finder<Integer, M_Customer> find =
            new Finder<Integer, M_Customer>(Integer.class, M_Customer.class);

}
