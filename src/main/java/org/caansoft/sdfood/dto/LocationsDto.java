package org.caansoft.sdfood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.caansoft.core.dto.BaseDto;


public class LocationsDto extends BaseDto {
	
	private String name;
	private String warehouseName;
	@JsonProperty(value = "product" , access = JsonProperty.Access.READ_WRITE)
	private ProductDto productDto;
	
//	@JsonProperty(value = "warehouse" , access = JsonProperty.Access.READ_WRITE)
//	private Warehouse warehouse;

	public ProductDto getProductDto() {
		return productDto;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

//	public Warehouse getWarehouse() {
//		return warehouse;
//	}
//
//	public void setWarehouse(Warehouse warehouse) {
//		this.warehouse = warehouse;
//	}
	
}
