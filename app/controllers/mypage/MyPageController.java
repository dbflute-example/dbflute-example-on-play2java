package controllers.mypage;

import com.google.inject.Guice;
import com.google.inject.Injector;

import dbflute.allcommon.DBFluteModule;
import play.*;
import play.db.DB;
import play.db.ebean.Transactional;
import play.mvc.*;
import views.html.*;

/**
 * @author jflute
 */
@Transactional
public class MyPageController extends Controller {

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }
}
