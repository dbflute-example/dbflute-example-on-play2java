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

import java.util.Random;

import org.springframework.transaction.annotation.Transactional;

import com.google.inject.Inject;

import docksidestage.dbflute.exbhv.MemberBhv;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.mypage.mypage;

/**
 * @author jflute
 * @author toshiaki.arai
 */
public class MyPageController extends Controller {
    
    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Inject
    protected MemberBhv memberBhv; 
    String name = "NANASHI";
    public MyPageWebBean bean;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    @Transactional
    public Result index() {
        // 試しに検索した名前を表示してみる
        
        memberBhv.selectEntity(cb -> {
            cb.setupSelect_MemberLoginAsLatest();
            cb.query().setMemberId_Equal(1); // seasea
        }).ifPresent(member -> {
            bean = new MyPageWebBean().initialize(member);
            name = member.getMemberName();
        });
        
        return ok(mypage.render(bean));
    }
}
