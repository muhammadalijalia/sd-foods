package org.caansoft.sdfood.dto;

public class InventoryOrderedDto {

	private Long id;
	private String name;	
	private String vendorName;
	private Double totalOrderedPallets;
	private Long totalReceivedPallets;
	private Long totalOrderedNumberOfUnits;
	private Double totalReceivedNumberOfUnits;
	private Long damagedQuantity;
	
	public Double getTotalOrderedPallets() {
		return totalOrderedPallets;
	}
	public void setTotalOrderedPallets(Double totalOrderedPallets) {
		this.totalOrderedPallets = totalOrderedPallets;
	}
	public Long getTotalReceivedPallets() {
		return totalReceivedPallets;
	}
	public void setTotalReceivedPallets(Long totalReceivedPallets) {
		this.totalReceivedPallets = totalReceivedPallets;
	}
	public Long getTotalOrderedNumberOfUnits() {
		return totalOrderedNumberOfUnits;
	}
	public void setTotalOrderedNumberOfUnits(Long totalOrderedNumberOfUnits) {
		this.totalOrderedNumberOfUnits = totalOrderedNumberOfUnits;
	}
	public Double getTotalReceivedNumberOfUnits() {
		return totalReceivedNumberOfUnits;
	}
	public void setTotalReceivedNumberOfUnits(Double totalReceivedNumberOfUnits) {
		this.totalReceivedNumberOfUnits = totalReceivedNumberOfUnits;
	}
	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getDamagedQuantity() {
		return damagedQuantity;
	}
	public void setDamagedQuantity(Long damagedQuantity) {
		this.damagedQuantity = damagedQuantity;
	}
}
