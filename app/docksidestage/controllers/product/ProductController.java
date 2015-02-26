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
import org.dbflute.optional.OptionalEntity;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.product.productList;
import views.html.product.productDisp;

import com.google.inject.Inject;

import docksidestage.dbflute.exbhv.ProductBhv;
import docksidestage.dbflute.exentity.Product;

/**
 * @author perrotta
 * @author jflute
 */
public class ProductController extends Controller {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    @Inject
    private ProductBhv productBhv;

    // ===================================================================================
    //                                                                             Execute
    //                                                                             =======
    public Result list() {
        Form<ProductSearchForm> request = Form.form(ProductSearchForm.class).bindFromRequest();
        ProductSearchForm form = request.get();
        List<ProductWebBean> productBeans = prepareProductBeans(form);
        return ok(productList.render(productBeans, request));
    }

    public Result disp(Integer prdId) {
        // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
        // TODO perrotta 排他制御はフレームワークでやるので、そのままgetしちゃってOK by jflute
        // e.g. 
        //  ProductWebBean bean = productBhv.selectEntity(cb -> cb.acceptPK(prdId)).map(product -> {
        //      return new ProductWebBean(product);
        //  }).get();
        //  return ok(productDisp.render(bean));
        //
        // こうもできるけど、さすがにこれは見づらいかなぁ...
        // e.g.
        //  return productBhv.selectEntity(cb -> cb.acceptPK(prdId)).map(product -> {
        //      return new ProductWebBean(product);
        //  }).map(bean -> {
        //      return ok(productDisp.render(bean));
        //  }).get();
        // _/_/_/_/_/_/_/_/_/_/
        OptionalEntity<ProductWebBean> product = productBhv.selectEntity(cb -> {
            cb.query().setProductId_Equal(prdId);
        }).map(prd -> {
            return new ProductWebBean(prd);
        });
        if (product.isPresent()) {
            return ok(productDisp.render(product.get()));
        } else {
            // TODO hirota :if selected product is end of sales you are sent Error screen
            return ok(productDisp.render(product.get()));
        }
    }

    // ===================================================================================
    //                                                                             Mapping
    //                                                                             =======
    private List<ProductWebBean> prepareProductBeans(ProductSearchForm form) {
        // TODO perrotta こんな感じで書いてごらん by jflute
        //return selectProducts(form).stream().map(product -> {
        //    return new ProductWebBean(product);
        //}).collect(Collectors.toList());
        ListResultBean<Product> products = selectProducts(form);
        List<ProductWebBean> productBeanList = new ArrayList<ProductWebBean>();
        products.forEach(prd -> {
            productBeanList.add(new ProductWebBean(prd));
        });
        return productBeanList;
    }

    private ListResultBean<Product> selectProducts(ProductSearchForm form) {
        return productBhv.selectList(cb -> {
            cb.ignoreNullOrEmptyQuery();
            cb.query().setRegularPrice_GreaterEqual(form.getFromPrice());
            cb.query().setRegularPrice_LessEqual(form.getToPrice());
            cb.query().addOrderBy_RegisterDatetime_Desc();
        });
    }
}
