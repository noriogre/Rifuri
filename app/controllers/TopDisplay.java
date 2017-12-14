package controllers;

import javax.inject.Inject;

import forms.DailyForm;
import forms.MonthlyForm;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.WEB_T_000;

public class TopDisplay extends Controller {

	@Inject
	private FormFactory formFactory;

    public Result showTop() {

    	Form<DailyForm> dailyForm = formFactory.form(DailyForm.class);
    	Form<MonthlyForm> monthlyForm = formFactory.form(MonthlyForm.class);

        return ok(WEB_T_000.render(dailyForm, monthlyForm));
    }
}
