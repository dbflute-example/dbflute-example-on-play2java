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

    private Form<ProductSearchForm> form = Form.form(ProductSearchForm.class);
    @Inject
    private ProductBhv productBhv;

    public Result list() {
        List<ProductWebBean> productBeanList = searchProducts();
        return ok(productList.render(productBeanList, form));
    }

    private List<ProductWebBean> searchProducts() {
        Form<ProductSearchForm> request = form.bindFromRequest();
        ListResultBean<Product> products = productBhv.selectList(cb -> {
            cb.query().setRegularPrice_GreaterEqual(Integer.valueOf(request.get().fromPrice));
            cb.query().setRegularPrice_LessEqual(Integer.valueOf(request.get().toPrice));
            cb.query().addOrderBy_RegisterDatetime_Desc();
        });
        List<ProductWebBean> productBeanList = new ArrayList<ProductWebBean>();
        for (Product product : products) {
            productBeanList.add(new ProductWebBean(product));
        }
        return productBeanList;
    }
}
