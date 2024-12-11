package org.caansoft.sdfood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.caansoft.core.dto.MediaDto;
import org.caansoft.sdfood.model.Category;
import org.caansoft.sdfood.prestashop.dto.ExpiryDTO;

import java.sql.Timestamp;
import java.util.List;
import org.caansoft.core.dto.BaseDto;

public class ProductDto extends BaseDto {

	private String name;
	@JsonProperty(value = "category" , access = JsonProperty.Access.READ_WRITE)
	private CategoryDto categoryDto;
	private Category subCategory;
	private String ref;
	private String description;
	private Float htPrice;
	private String unit;
	private String tva;
	//	@JsonProperty(value = "partner" , access = JsonProperty.Access.READ_WRITE)
	//	private PartnerDto partnerDto;
	private List<OrderDto> order;
	private List<ProductLocationDto> productLocation;
	private List<MediaDto> photos;
	private Float quantity;
	private Integer  numberOfUnits;
	private Boolean frozen;
	private Long orderProductId;
	private Integer prestashopProductId;
	private String action;
	private Boolean isExpiry;
	private Boolean isFresh;	
	@JsonProperty(value = "fields")
	private List<ExpiryDTO> fields;
	private String imageUrl;
	
	@JsonProperty(value = "otherName")
	private ProductNameDto productNameDto;
	
	@JsonProperty(value = "otherNames")
	private List<ProductNameDto> productNameDtoList;
	
	public List<ProductNameDto> getProductNameDtoList() {
		return productNameDtoList;
	}

	public void setProductNameDtoList(List<ProductNameDto> productNameDtoList) {
		this.productNameDtoList = productNameDtoList;
	}

	public ProductNameDto getProductNameDto() {
		return productNameDto;
	}

	public void setProductNameDto(ProductNameDto productNameDto) {
		this.productNameDto = productNameDto;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<ExpiryDTO> getFields() {
		return fields;
	}

	public void setFields(List<ExpiryDTO> fields) {
		this.fields = fields;
	}

	public Boolean getIsExpiry() {
		return isExpiry;
	}

	public void setIsExpiry(Boolean isExpiry) {
		this.isExpiry = isExpiry;
	}

	public Boolean getIsFresh() {
		return isFresh;
	}

	public void setIsFresh(Boolean isFresh) {
		this.isFresh = isFresh;
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}

	public List<ProductLocationDto> getProductLocation() {
		return productLocation;
	}

	public void setProductLocation(List<ProductLocationDto> productLocation) {
		this.productLocation = productLocation;
	}

	public List<MediaDto> getPhotos() {
		return photos;
	}

	public void setPhotos(List<MediaDto> photos) {
		this.photos = photos;
	}

	public List<OrderDto> getOrder() {
		return order;
	}
	public void setOrder(List<OrderDto> order) {
		this.order = order;
	}

	public CategoryDto getCategoryDto() {
		return categoryDto;
	}

	public void setCategoryDto(CategoryDto categoryDto) {
		this.categoryDto = categoryDto;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Category subCategory) {
		this.subCategory = subCategory;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getHtPrice() {
		return htPrice;
	}

	public void setHtPrice(Float htPrice) {
		this.htPrice = htPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getTva() {
		return tva;
	}

	public void setTva(String tva) {
		this.tva = tva;
	}


	public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

	public Long getOrderProductId() {
		return orderProductId;
	}

	public void setOrderProductId(Long orderProductId) {
		this.orderProductId = orderProductId;
	}

	public Integer getPrestashopProductId() {
		return prestashopProductId;
	}

	public void setPrestashopProductId(Integer prestashopProductId) {
		this.prestashopProductId = prestashopProductId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getNumberOfUnits() {
		return numberOfUnits;
	}

	public void setNumberOfUnits(Integer numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}
}
