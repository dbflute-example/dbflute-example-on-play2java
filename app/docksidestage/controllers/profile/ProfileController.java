package docksidestage.controllers.profile;

import java.time.LocalDate;

import org.springframework.transaction.annotation.Transactional;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.profile.profile;
import views.html.profile.profile_edit;

import com.google.inject.Inject;

import docksidestage.dbflute.exbhv.MemberBhv;

/**
 * @author toshiaki.arai
 */
public class ProfileController extends Controller {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Inject
    protected MemberBhv memberBhv;
    public ProfileWebBean bean;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Transactional
    public Result index() {

        memberBhv.selectEntity(cb -> {
            cb.setupSelect_MemberAddressAsValid(LocalDate.now());
            cb.setupSelect_MemberSecurityAsOne();
            cb.setupSelect_MemberServiceAsOne();
            cb.setupSelect_MemberStatus();
            cb.query().setMemberId_Equal(1);
        }).alwaysPresent(member -> {
            bean = new ProfileWebBean().initialize(member);
        });
        return ok(profile.render(bean));
    }

    @Transactional
    public Result edit() {
        return ok(profile_edit.render());
    }
}
