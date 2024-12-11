package org.caansoft.sdfood.dto;

public class ProductStockBalanceDTO {

	private Long id;
	private Long sumQuantityIn;
	private Long sumQuantityOut;
	private Long balance;
	
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSumQuantityIn() {
		return sumQuantityIn;
	}
	public void setSumQuantityIn(Long sumQuantityIn) {
		this.sumQuantityIn = sumQuantityIn;
	}
	public Long getSumQuantityOut() {
		return sumQuantityOut;
	}
	public void setSumQuantityOut(Long sumQuantityOut) {
		this.sumQuantityOut = sumQuantityOut;
	}
	
	
}
