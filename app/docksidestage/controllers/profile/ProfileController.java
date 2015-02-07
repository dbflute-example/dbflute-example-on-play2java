package docksidestage.controllers.profile;

import org.springframework.transaction.annotation.Transactional;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.profile;

/**
 * @author toshiaki.arai
 */
public class ProfileController extends Controller {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    
    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Transactional
    public Result index() {
        return ok(profile.render());
    }
}
