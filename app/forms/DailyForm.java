package forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.avaje.ebean.Model;

import lombok.Getter;
import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;

public class DailyForm extends Model {

	/** 年月日 */
    @Required(message = "閲覧年月日を入力してください。")
    @DateTime(pattern = "yyyy-MM-dd")
    @Getter
    public Date targetDate;


	/**
	 * 入力データの妥当性チェック
	 * @return エラー情報
	 */
	public List<ValidationError> validate(){
		List<ValidationError> errors = new ArrayList<ValidationError>();
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateFormat.format(date);
		System.out.println(dateStr);

		String targetDateStr = dateFormat.format(targetDate);
		System.out.println(targetDateStr);
		System.out.println(dateStr.compareTo(targetDateStr));

		if(dateStr.compareTo(targetDateStr) <= 0){
			errors.add(new ValidationError("date","閲覧年月日には本日以前の日付を入力してください。"));
		}

		return errors.isEmpty() ? null : errors;
	}
}
