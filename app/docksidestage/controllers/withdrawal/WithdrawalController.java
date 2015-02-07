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
package docksidestage.controllers.withdrawal;

import org.springframework.transaction.annotation.Transactional;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.withdrawal.withdrawal;

/**
 * @author jflute
 */
public class WithdrawalController extends Controller {

    @Transactional
    public Result index() {
        return ok(withdrawal.render("Your new withdrawal is ready."));
    }
}