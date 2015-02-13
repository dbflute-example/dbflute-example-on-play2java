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
package docksidestage.controllers.product;

import java.util.ArrayList;
import java.util.List;

import org.dbflute.cbean.result.ListResultBean;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.product.productList;

import com.google.inject.Inject;

import docksidestage.dbflute.exbhv.ProductBhv;
import docksidestage.dbflute.exentity.Product;

/**
 * @author perrotta
 */
public class ProductController extends Controller {

    @Inject
    private ProductBhv productBhv;

    public Result list() {
        Form<ProductSearchForm> request = Form.form(ProductSearchForm.class).bindFromRequest();
        System.out.println("dfafadfaff ff" + request);
        ProductSearchForm form = request.get();
        System.out.println("テストですちょ" + form.getFromPrice() + "テストまただ" + form.getToPrice());
        List<ProductWebBean> productBeanList = searchProducts(form);
        return ok(productList.render(productBeanList, request));
    }

    private List<ProductWebBean> searchProducts(ProductSearchForm form) {
        ListResultBean<Product> products = productBhv.selectList(cb -> {
            cb.ignoreNullOrEmptyQuery();
            cb.query().setRegularPrice_GreaterEqual(form.getFromPrice());
            cb.query().setRegularPrice_LessEqual(form.getToPrice());
            cb.query().addOrderBy_RegisterDatetime_Desc();
        });
        List<ProductWebBean> productBeanList = new ArrayList<ProductWebBean>();
        for (Product product : products) {
            productBeanList.add(new ProductWebBean(product));
        }
        return productBeanList;
    }
}
