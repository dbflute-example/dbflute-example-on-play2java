package docksidestage.controllers.product;

import docksidestage.dbflute.exentity.Product;

public class ProductWebBean {
    public Integer productId;

    public String productHandleCode;

    public String productName;

    public Integer price;

    public ProductWebBean(Product product) {
        productId = product.getProductId();
        productHandleCode = product.getProductHandleCode();
        productName = product.getProductName();
        price = product.getRegularPrice();
    }

}
