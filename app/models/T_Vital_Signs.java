package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.CreatedTimestamp;

/**
 *
 * バイタル情報エンティティクラス
 *
 */

@Entity
@Table(name = "t_vital_signs")
public class T_Vital_Signs extends Model {

	/**
	 * 利用者ID
	 */
//	@Column(name = "customer_id")
	@ManyToOne
	@JoinColumn(name = "customer_id")
	public M_Customer customer;
//	public Integer customerId;

	/**
	 * 体温
	 */
	@Column(name = "body_temperature")
	public Double bodyTemperature;

	/**
	 * 最低血圧
	 */
	@Column(name = "low_blood_pressure")
	public Integer lowBloodPressure;

	/**
	 * 最高血圧
	 */
	@Column(name = "high_blood_pressure")
	public Integer highBloodPressure;

	/**
	 * 脈拍
	 */
	@Column(name = "heart_rate")
	public Integer heartRate;

	/**
	 * 意識
	 */
	@Column(name = "coma_scale")
	public Integer comaScale;

	/**
	 * 呼吸
	 */
	@Column(name = "respiration_rate")
	public String respirationRate;

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


    public static Finder<Long, T_Vital_Signs> find =
            new Finder<Long, T_Vital_Signs>(Long.class, T_Vital_Signs.class);

}
