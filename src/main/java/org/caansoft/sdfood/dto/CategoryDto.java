package org.caansoft.sdfood.dto;

import org.caansoft.core.dto.BaseDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDto extends BaseDto {
    private String name;
    private String type;
    private Integer prestashopCategoryId;
    private String action;
    @JsonProperty(value = "product" , access = JsonProperty.Access.READ_WRITE)
	private ProductDto productDto;
    
    public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrestashopCategoryId() {
        return prestashopCategoryId;
    }

    public void setPrestashopCategoryId(Integer prestashopCategoryId) {
        this.prestashopCategoryId = prestashopCategoryId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
