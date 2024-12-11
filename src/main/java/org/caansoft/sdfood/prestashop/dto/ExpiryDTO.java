package org.caansoft.sdfood.prestashop.dto;

import org.caansoft.core.dto.BaseDto;

import java.sql.Timestamp;

public class ExpiryDTO extends BaseDto {

	private Timestamp expiry;
	private Long quantity;
	private String lotNumber;
	
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
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
}
