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
package docksidestage.controllers.mypage;

import org.springframework.transaction.annotation.Transactional;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.mypage.mypage;

import com.google.inject.Inject;

import docksidestage.dbflute.exbhv.MemberBhv;

/**
 * @author jflute
 * @author toshiaki.arai
 * @author jun_0915
 */
public class MyPageController extends Controller {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Inject
    protected MemberBhv memberBhv;
    public MyPageWebBean bean;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Transactional
    public Result index() {
        // TODO toshiaki.arai for test environment
        //String sessionId = session("memberId");
        //if (sessionId == null) {
        //    return redirect("/");
        //}
        MyPageWebBean bean = memberBhv.selectEntity(cb -> {
            cb.setupSelect_MemberLoginAsLatest();
            cb.query().setMemberId_Equal(1);
        }).map(member -> {
            return new MyPageWebBean().initialize(member);
        }).get();

        return ok(mypage.render(bean));
    }

    public Result logout() {
        session().remove("memberId");
        return redirect("/");
    }
}
