package docksidestage.controllers.product;

import docksidestage.dbflute.exentity.Product;

public class ProductWebBean {
    public Integer productId;

    public String productHandleCode;

    public String productName;

    public ProductWebBean(Product product) {
        productId = product.getProductId();
        productHandleCode = product.getProductHandleCode();
        productName = product.getProductName();
    }

    public String getProductHandleCode() {
        return productHandleCode;
    }

    public void setProductHandleCode(String productHandleCode) {
        this.productHandleCode = productHandleCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

}
