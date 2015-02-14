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
package docksidestage.controllers.purchase;

import org.dbflute.optional.OptionalEntity;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.purchase.purchase;

import com.google.inject.Inject;

import docksidestage.controllers.product.ProductWebBean;
import docksidestage.dbflute.exbhv.ProductBhv;
import docksidestage.dbflute.exentity.Product;

/**
 * @author perrotta
 */
public class PurchaseController extends Controller {

    @Inject
    private ProductBhv productBhv;

    public Result purchase() {
        Form<PurchaseForm> request = Form.form(PurchaseForm.class).bindFromRequest();
        // TODO hirota :add validations product is shortage and bad request
        PurchaseForm form = request.get();
        OptionalEntity<Product> optionalProduct = productBhv.selectEntity(cb -> {
            cb.query().setProductId_Equal(form.getProductId());
        });
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.isProductStatusCode生産販売可能()) {
                return ok(purchase.render(new ProductWebBean(product)));
            } else {
                // product is shortage
                return ok(purchase.render(new ProductWebBean(product)));
            }
        } else {
            // bad request
            return ok(purchase.render(new ProductWebBean(null)));
        }
    }
}
