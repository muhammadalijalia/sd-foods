package org.caansoft.sdfood.model;

import java.sql.Timestamp;

import javax.persistence.*;

import org.caansoft.core.model.BaseEntity;

@Entity
@Table(name = "stockcount_line_items")
public class StockCountLineItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "external_system_id")
    private Long externalSystemId;

    @Column(name = "external_system_name")
    private String externalSystemName;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "stockcount_header_id")
    private StockCountHeader stockCountHeader;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getExternalSystemId() {
        return externalSystemId;
    }

    public void setExternalSystemId(Long externalSystemId) {
        this.externalSystemId = externalSystemId;
    }

    public String getExternalSystemName() {
        return externalSystemName;
    }

    public void setExternalSystemName(String externalSystemName) {
        this.externalSystemName = externalSystemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public StockCountHeader getStockCountHeader() {
        return stockCountHeader;
    }

    public void setStockCountHeader(StockCountHeader stockCountHeader) {
        this.stockCountHeader = stockCountHeader;
    }
}
