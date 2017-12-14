package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.InsertDataTest;

public class InsertDateTest extends Controller {

    public Result showInsertData() {
        return ok(InsertDataTest.render());
    }
}
