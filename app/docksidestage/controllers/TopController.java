/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package docksidestage.controllers;

import javax.validation.Validation;

import org.dbflute.optional.OptionalEntity;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;

import com.google.inject.Inject;

import docksidestage.dbflute.exbhv.MemberBhv;
import docksidestage.dbflute.exentity.Member;

/**
 * @author jflute
 * @author jun_0915
 */
public class TopController extends Controller {

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    private Form<LoginForm> loginForm = Form.form(LoginForm.class);

    // -----------------------------------------------------
    //                                          DI Component
    //                                          ------------
    @Inject
    protected MemberBhv memberBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Result index() {
        return ok(login.render(loginForm));
    }

    public Result doLogin() {
        DynamicForm request = Form.form().bindFromRequest();
        if (!request.hasErrors()) {
            OptionalEntity<Member> member = memberBhv.selectEntity(cb -> {
                cb.query().setMemberName_Equal(request.get("name"));
                cb.query().queryMemberSecurityAsOne().setLoginPassword_Equal(request.get("password"));
            });
            if (member.isPresent()) {
                return redirect("/mypage");
            }
        }
        // TODO jun_0915 add RedirectIndex() and improve formRequest
        return badRequest("error");
    }
}
