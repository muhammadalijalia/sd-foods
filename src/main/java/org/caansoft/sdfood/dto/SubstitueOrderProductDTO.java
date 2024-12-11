package org.caansoft.sdfood.dto;

import java.util.List;

import org.caansoft.core.dto.MediaDto;
import org.caansoft.core.model.Media;

public class SubstitueOrderProductDTO {

	private Long orderId;
	private Long productId;
	private Long expiry;
	private Integer receivedQuantity;
	private Float damagedQuantity;
	private String lotNumber;
	private Long locationId;
	private Integer receivedPallets;
	private Integer receivedNumberOfUnits;
	private Integer damagedNumberOfUnits;
	private List<MediaDto> photos;
	
	public List<MediaDto> getPhotos() {
		return photos;
	}
	public void setPhotos(List<MediaDto> photos) {
		this.photos = photos;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getExpiry() {
		return expiry;
	}
	public void setExpiry(Long expiry) {
		this.expiry = expiry;
	}
	public Integer getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(Integer receivedQuantity) {
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
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Integer getReceivedPallets() {
		return receivedPallets;
	}
	public void setReceivedPallets(Integer receivedPallets) {
		this.receivedPallets = receivedPallets;
	}
	public Integer getReceivedNumberOfUnits() {
		return receivedNumberOfUnits;
	}
	public void setReceivedNumberOfUnits(Integer receivedNumberOfUnits) {
		this.receivedNumberOfUnits = receivedNumberOfUnits;
	}
	public Integer getDamagedNumberOfUnits() {
		return damagedNumberOfUnits;
	}
	public void setDamagedNumberOfUnits(Integer damagedNumberOfUnits) {
		this.damagedNumberOfUnits = damagedNumberOfUnits;
	}
	
}
