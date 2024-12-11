package org.caansoft.sdfood.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.caansoft.core.model.BaseEntity;

@Entity
@Table(name="prestashop_orders_list")
public class PrestashopOrderDetails extends BaseEntity {
	
	@Column(name="order_id")
	private Integer orderId;
	
	@Column(name="product_id")
	private String productId;
	
	@Column(name="product_name")
	private String productName;
	
	@Column(name="product_quantity")
	private Integer productQuantity;
	
	@Column(name="product_unit_price")
	private Integer productUnitPrice;
	
	@Column(name="delivery_Status")
	private String deliveryStatus;
	
	@Column(name="product_total_price")
	private Integer productTotalPrice;
	
	@Column(name="unit_price_tax")
	private Integer unitPriceTax;
	
	@Column(name="delivery_date")
	private Timestamp deliveryDate;
	
	@Column(name="product_stock")
	private Integer productStock;
	
	@Column(name="expiry")
	private Timestamp expiry;
	
	
	public Timestamp getExpiry() {
		return expiry;
	}

	public void setExpiry(Timestamp expiry) {
		this.expiry = expiry;
	}

	public Integer getProductStock() {
		return productStock;
	}

	public void setProductStock(Integer productStock) {
		this.productStock = productStock;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Integer getProductUnitPrice() {
		return productUnitPrice;
	}

	public void setProductUnitPrice(Integer productUnitPrice) {
		this.productUnitPrice = productUnitPrice;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Integer getProductTotalPrice() {
		return productTotalPrice;
	}

	public void setProductTotalPrice(Integer productTotalPrice) {
		this.productTotalPrice = productTotalPrice;
	}

	public Integer getUnitPriceTax() {
		return unitPriceTax;
	}

	public void setUnitPriceTax(Integer unitPriceTax) {
		this.unitPriceTax = unitPriceTax;
	}

	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Timestamp deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	
}
