package org.caansoft.sdfood.prestashop.dto;

import java.util.List;

import org.caansoft.sdfood.dto.ProductDto;

public class ProductExpiryDetailDTO {

	private Integer orderId;
	private String updatedBy;
	private String message;
	private Boolean fake;
	
	public Boolean getFake() {
		return fake;
	}
	public void setFake(Boolean fake) {
		this.fake = fake;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	private List<ProductDto> products;
	
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public List<ProductDto> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}
}
