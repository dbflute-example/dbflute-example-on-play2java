package docksidestage.controllers.signin;

import play.data.validation.Constraints.Required;

/**
 * @author jun_0915
 */
public class SignInForm {

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    @Required(message = "必須項目です")
    public String memberName;
    @Required(message = "必須項目です")
    public String password;
}
