package forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Model;

import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;

public class MonthlyForm extends Model {

	/** 年月 */
	@Required(message = "閲覧年月を入力してください。")
	@DateTime(pattern = "yyyy-MM")
	public String targetMonth;


	/** 利用者名 */
	@Required(message = "利用者名を入力してください。")
	@Pattern(value = "^[ぁ-んァ-ヶー一-龠]+$", message = "利用者名は全角で入力してください。")
	@MaxLength(value = 50, message = "利用者名は50桁以下で入力してください。")
	public String customerName;


	/** 介護保険被保険者証番号 */
	@Pattern(value = "^[a-zA-Z0-9]+$", message = "被保険者番号は半角英数字で入力してください。")
	@MinLength(value = 10, message = "被保険者番号は10桁で入力してください。")
	@MaxLength(value = 10, message = "被保険者番号は10桁で入力してください。")
	public String customerNum;


	/**
	 * 入力データの妥当性チェック
	 * @return エラー情報
	 */
	public List<ValidationError> validate(){
		List<ValidationError> errors = new ArrayList<>();
		Date date = new Date();
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
		String monthStr = monthFormat.format(date);
		System.out.println(monthStr);

		if(monthStr.compareTo(targetMonth) >= 0){
			errors.add(new ValidationError("month", "閲覧年月には本日以前の年月を入力してください。"));
		}

		return errors.isEmpty() ? null : errors;
	}
}
