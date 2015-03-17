package docksidestage.controllers.product;

import docksidestage.dbflute.exentity.Product;

/**
 * @author perrotta
 */
public class ProductWebBean {

    public Integer productId;
    public String productHandleCode;
    public String productName;
    public Integer price;
    public boolean purchasableFlg;

    public ProductWebBean(Product product) {
        productId = product.getProductId();
        productHandleCode = product.getProductHandleCode();
        productName = product.getProductName();
        price = product.getRegularPrice();
        purchasableFlg = product.isProductStatusCodeOnSaleProduction();
    }
}
