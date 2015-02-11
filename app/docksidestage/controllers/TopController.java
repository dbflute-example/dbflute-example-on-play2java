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

import static play.data.Form.form;

import org.dbflute.optional.OptionalEntity;

import play.data.Form;
import play.data.validation.Constraints.Required;
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
    // -----------------------------------------------------
    //                                          DI Component
    //                                          ------------
    @Inject
    protected MemberBhv memberBhv;

    // ====================================================================================
    //                                                                            LoginForm
    //                                                                            =========
    public static class LoginForm {
        @Required(message = "必須項目です")
        public String memberName;
        @Required(message = "必須項目です")
        public String password;
    }

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Result index() {
        return ok(login.render(new Form<LoginForm>(LoginForm.class)));
    }

    public Result doLogin() {
        Form<LoginForm> request = form(LoginForm.class).bindFromRequest();
        if (!request.hasErrors()) {
            OptionalEntity<Member> member = getMember(request);
            if (member.isPresent()) {
                session("memberId", member.get().getMemberId().toString());
                return redirect("/mypage");
            } else {
                return badRequest(request.errorsAsJson());
                // TODO jun_0915 検索していなかったら、"passwordもしくはmemberNameを確かめてね"的なことを出す。
            }
        }
        // TODO jun_0915 memberId,passwordどちらかがnull or 0なら"必須です"って出す
        return badRequest(request.errorsAsJson());
    }

    /**
     * 対象の会員を検索
     * @param request (NotNull)
     * @return member (NullAllowed)
     */
    private OptionalEntity<Member> getMember(Form<LoginForm> request) {
        OptionalEntity<Member> member = memberBhv.selectEntity(cb -> {
            cb.query().setMemberName_Equal(request.get().memberName);
            cb.query().queryMemberSecurityAsOne().setLoginPassword_Equal(request.get().password);
        });
        return member;
    }
}
