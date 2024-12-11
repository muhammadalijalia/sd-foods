package org.caansoft.sdfood.prestashop.dto;

public class PrestashopOrderSalesDTO {

	private Integer id;
	private Integer ordersQuantity;
	private Float orderSales;

	public Integer getOrdersQuantity() {
		return ordersQuantity;
	}

	public void setOrdersQuantity(Integer ordersQuantity) {
		this.ordersQuantity = ordersQuantity;
	}

	public Float getOrderSales() {
		return orderSales;
	}

	public void setOrderSales(Float orderSales) {
		this.orderSales = orderSales;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
