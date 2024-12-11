package org.caansoft.sdfood.prestashop.dto;

import java.util.List;

import org.caansoft.sdfood.dto.ProductLocationDto;

public class ExpiriesDTO {

	private Long productId;
	private List<ProductLocationDto> productLocationDtoList;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public List<ProductLocationDto> getProductLocationDtoList() {
		return productLocationDtoList;
	}
	public void setProductLocationDtoList(List<ProductLocationDto> productLocationDtoList) {
		this.productLocationDtoList = productLocationDtoList;
	}
}
