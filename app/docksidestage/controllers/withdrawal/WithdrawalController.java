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

// TODO jflute play2: ログインチェックどうするのー？
/**
 * @author jflute
 */
public class WithdrawalController extends Controller {

    // TODO jflute play2: 退会画面、画面表示リクエストと、退会処理リクエストを実装すべし
    public Result index() {
        // すでに退会してたら MyPage へ飛ばす
        // 退会理由のリストボックスを表示
        return ok(withdrawal.render("Your new withdrawal is ready."));
    }

    @Transactional
    public Result withdraw() {
        // ステータスの変更
        // 退会情報の登録
        // 強制ログアウト
        // 退会おめでとう画面の表示
        return ok(withdrawal.render("Your new withdrawal is ready."));
    }
}
