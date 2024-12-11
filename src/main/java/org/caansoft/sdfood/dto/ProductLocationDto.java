package org.caansoft.sdfood.dto;

import java.sql.Timestamp;

import org.caansoft.core.dto.BaseDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductLocationDto extends BaseDto {
	private Long locationId;
	private Float receivedQuantity;
	private Float damagedQuantity;
	private String lotNumber;
	private Timestamp expiry;
	private Float receivedPallets;
	private Integer receivedNumberofUnits;
	private Integer damageNumberOfUnits;
	
	@JsonProperty(value = "product" , access = JsonProperty.Access.READ_WRITE)
	private ProductDto productDto;
    
    public Float getReceivedPallets() {
		return receivedPallets;
	}

	public void setReceivedPallets(Float receivedPallets) {
		this.receivedPallets = receivedPallets;
	}

	public Integer getReceivedNumberofUnits() {
		return receivedNumberofUnits;
	}

	public void setReceivedNumberofUnits(Integer receivedNumberofUnits) {
		this.receivedNumberofUnits = receivedNumberofUnits;
	}

	public Integer getDamageNumberOfUnits() {
		return damageNumberOfUnits;
	}

	public void setDamageNumberOfUnits(Integer damageNumberOfUnits) {
		this.damageNumberOfUnits = damageNumberOfUnits;
	}

	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public Float getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(Float receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public Float getDamagedQuantity() {
		return damagedQuantity;
	}

	public void setDamagedQuantity(Float damagedQuantity) {
		this.damagedQuantity = damagedQuantity;
	}

	public String getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}

	public Timestamp getExpiry() {
		return expiry;
	}

	public void setExpiry(Timestamp expiry) {
		this.expiry = expiry;
	}


}
