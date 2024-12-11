package org.caansoft.sdfood.dto;

import java.util.List;

public class AdjustmentDTO {

	private Integer id;
	private String name;
	private Long externalSystemId;	
	private Long expiry;
	private Long recordedDate;
	private Integer quantity;
	private String lotNumber;
	private Integer balance;
	private String transactionType;
	private Long recordedTime;
	private Long selectedDate;
	private Integer prestashopProductId;
	
	public Integer getPrestashopProductId() {
		return prestashopProductId;
	}
	public void setPrestashopProductId(Integer prestashopProductId) {
		this.prestashopProductId = prestashopProductId;
	}
	public Long getSelectedDate() {
		return selectedDate;
	}
	public void setSelectedDate(Long selectedDate) {
		this.selectedDate = selectedDate;
	}
	public Long getExpiry() {
		return expiry;
	}
	public void setExpiry(Long expiry) {
		this.expiry = expiry;
	}
	public Long getRecordedDate() {
		return recordedDate;
	}
	public void setRecordedDate(Long recordedDate) {
		this.recordedDate = recordedDate;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Long getRecordedTime() {
		return recordedTime;
	}
	public void setRecordedTime(Long recordedTime) {
		this.recordedTime = recordedTime;
	}
	public Long getExternalSystemId() {
		return externalSystemId;
	}
	public void setExternalSystemId(Long externalSystemId) {
		this.externalSystemId = externalSystemId;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
