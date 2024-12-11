package org.caansoft.sdfood.prestashop.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;
import org.caansoft.core.model.BaseEntity;


@Transactional
@Entity
@Table(name="product_expiry_detail")
public class ProductExpiryDetail extends BaseEntity{
	
	@Column(name="order_id")
	private Integer orderId;

	@Column(name="product_id")
	private Long productId;
	
	private String name;
	private Timestamp expiry;
	
	@Column(name="quantity_sold")
	private Long quantityPrepared;
	
	@Column(name="ordered_quantity")
	private Float orderQuantity;
	
	@Column
	private String lotNumber;
	
	public ProductExpiryDetail() {};
	
	public ProductExpiryDetail (Long productId, Long quantity, Integer orderId) {
		this.orderId = orderId;
		this.quantityPrepared = quantity;
		this.productId = productId;
	}

	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	public Float getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(Float orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getExpiry() {
		return expiry;
	}
	public void setExpiry(Timestamp expiry) {
		this.expiry = expiry;
	}
	public Long getQuantityPrepared() {
		return quantityPrepared;
	}
	public void setQuantityPrepared(Long quantityPrepared) {
		this.quantityPrepared = quantityPrepared;
	}
}
