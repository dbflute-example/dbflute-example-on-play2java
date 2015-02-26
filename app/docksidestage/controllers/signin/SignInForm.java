package docksidestage.controllers.signin;

import play.data.validation.Constraints.Required;

import com.google.inject.Inject;

import docksidestage.dbflute.exbhv.MemberBhv;

/**
 * @author jun_0915
 */
public class SignInForm {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Required(message = "error.required")
    public String memberName;
    @Required(message = "error.required")
    public String password;

    // -----------------------------------------------------
    //                                          DI Component
    //                                          ------------
    @Inject
    protected MemberBhv memberBhv;

    // ===================================================================================
    //                                                                          Validation
    //                                                                          ==========
    // http://mpon.hatenablog.com/entry/2014/08/06/023123
    //    public String validate() {
    //        if (authenticate(memberName, password)) {
    //            return "Invalid memberName or password";
    //        }
    //        return null;
    //    }

    // ==================================================================================
    //                                                                            private
    //                                                                            =======
    //private Boolean authenticate(String memberName, String password) {
    //    OptionalEntity<Member> member = memberBhv.selectEntity(cb -> {
    //        cb.query().setMemberName_Equal(memberName);
    //        cb.query().queryMemberSecurityAsOne().setLoginPassword_Equal(password);
    //    });
    //    return member != null;
    //}
}
