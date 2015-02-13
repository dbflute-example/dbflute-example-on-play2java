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
package docksidestage.controllers.signin;

import org.dbflute.optional.OptionalEntity;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.signin.signin;

import com.google.inject.Inject;

import docksidestage.controllers.mypage.routes;
import docksidestage.dbflute.exbhv.MemberBhv;
import docksidestage.dbflute.exentity.Member;

/**
 * @author jflute
 * @author jun_0915
 */
public class SignInController extends Controller {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    // -----------------------------------------------------
    //                                          DI Component
    //                                          ------------
    @Inject
    protected MemberBhv memberBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Result index() {
        return ok(signin.render(Form.form(SignInForm.class)));
    }

    public Result doLogin() {
        Form<SignInForm> request = Form.form(SignInForm.class).bindFromRequest();
        if (!request.hasErrors()) {
            OptionalEntity<Member> member = selectMember(request);
            if (member.isPresent()) {
                session("memberId", member.get().getMemberId().toString());
                return redirect(routes.MyPageController.index());
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
    private OptionalEntity<Member> selectMember(Form<SignInForm> request) {
        OptionalEntity<Member> member = memberBhv.selectEntity(cb -> {
            cb.query().setMemberName_Equal(request.get().memberName);
            cb.query().queryMemberSecurityAsOne().setLoginPassword_Equal(request.get().password);
        });
        return member;
    }
}
