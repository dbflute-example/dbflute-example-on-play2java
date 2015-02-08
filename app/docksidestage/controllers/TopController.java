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

import org.dbflute.optional.OptionalEntity;
import org.springframework.transaction.annotation.Transactional;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.login;
import views.html.mypage.mypage;

import com.google.inject.Inject;

import docksidestage.controllers.mypage.MyPageWebBean;
import docksidestage.dbflute.exbhv.MemberBhv;
import docksidestage.dbflute.exentity.Member;

/**
 * @author jflute
 * @author jun_0915
 */
public class TopController extends Controller {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    final Form<LoginForm> form = Form.form(LoginForm.class);
    public MyPageWebBean bean;

    // -----------------------------------------------------
    //                                          DI Component
    //                                          ------------
    @Inject
    protected MemberBhv memberBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Transactional
    public Result index() {
        // TODO jflute example: Play2 fitting
        memberBhv.selectEntity(cb -> {
            cb.setupSelect_MemberStatus();
            cb.acceptPK(1);
        }).alwaysPresent(member -> {
            member.setMemberName("seasea");
            memberBhv.update(member);
        });
        return ok(login.render(form));
    }

    @Transactional
    public Result doLogin() {
        OptionalEntity<Member> member = memberBhv.selectEntity(cb -> {
            cb.setupSelect_MemberSecurityAsOne();
            cb.query().setMemberName_Equal("toyoda");
            cb.query().queryMemberSecurityAsOne().setLoginPassword_Equal("pixiy");
        });
        if (!member.isPresent()) {
            return this.index();
        }
        bean = new MyPageWebBean().initialize(member.get());
        return ok(mypage.render(bean));
    }
}
