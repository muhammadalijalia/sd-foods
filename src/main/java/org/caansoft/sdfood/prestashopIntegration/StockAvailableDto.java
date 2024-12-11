package org.caansoft.sdfood.prestashopIntegration;

import javax.xml.bind.annotation.XmlElement;

public class StockAvailableDto {
    private Integer id;
    private Integer productId;
    private Integer productAttributeId;
    private Integer shopId;
    private Integer quantity;
    private Integer dependsOnStock;
    private Integer outOfStock;

    @XmlElement
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "id_product")
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    @XmlElement(name = "id_shop")
    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @XmlElement(name = "id_product_attribute")
    public Integer getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(Integer productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @XmlElement(name = "depends_on_stock")
    public Integer getDependsOnStock() {
        return dependsOnStock;
    }

    public void setDependsOnStock(Integer dependsOnStock) {
        this.dependsOnStock = dependsOnStock;
    }

    @XmlElement(name = "out_of_stock")
    public Integer getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(Integer outOfStock) {
        this.outOfStock = outOfStock;
    }
}
