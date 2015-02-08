package docksidestage.controllers;

import play.data.validation.Constraints.Required;

/**
 * @author jun_0915
 */
public class LoginForm {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Required(message = "必須入力です")
    public String name;
    @Required(message = "必須入力です")
    public String password;
}
