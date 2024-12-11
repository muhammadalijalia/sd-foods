package org.caansoft.sdfood.dto;

import org.caansoft.core.dto.BaseDto;


public class StockCountLineItemDto extends BaseDto {

    private Long productId;
    private String productName;

    private Long externalSystemId;

    private String externalSystemName;

    private Integer quantity;

    private StockCountHeaderDto stockCountHeaderDto;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public StockCountHeaderDto getStockCountHeaderDto() {
        return stockCountHeaderDto;
    }

    public void setStockCountHeaderDto(StockCountHeaderDto stockCountHeaderDto) {
        this.stockCountHeaderDto = stockCountHeaderDto;
    }
}
