package org.caansoft.sdfood.prestashop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.caansoft.core.model.BaseEntity;
import org.caansoft.sdfood.model.Product;

@Transactional
@Entity
@Table(name="prestashop_order_products")
public class PrestashopOrderProducts extends BaseEntity{

	@Column(name="product_id")
	private Integer productId;
	
	@Column(name="product_name")
	private String productName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="prestashop_order_id")
	private PrestashopOrders order;

	@Column(name="product_detail_id")
	private Integer productDetailId;
	
	@Column(name="product_price")
	private Float productPrice;
	
	@Column(name="product_price_tax_incl")
	private Float producdPriceTaxIncl;
	
	@Column(name="product_quantity")
	private Integer productQuantity;
	
	@Column(name = "cost_price")
	private Float productCostPrice;

	@Transient
	private Long productTotalQuantity;
	
	@Transient
	private Double productTotalSales;
	
	@Transient
	private Double productTotalSalesTaxExcl;

	@Transient
	private Double taxValue;
	
	@Transient
	private Double totalCostPrice;
	
	public Double getTotalCostPrice() {
		return totalCostPrice;
	}

	public void setTotalCostPrice(Double totalCostPrice) {
		this.totalCostPrice = totalCostPrice;
	}

	public PrestashopOrderProducts (Integer id, String name, Long totalQuantity, Double totalSales,
			Double productTotalSalesTaxExcl, Double taxValue, Double costPrice) {
		
		this.productId = id;
		this.productName = name;
		this.productTotalQuantity = totalQuantity;
		this.productTotalSales = totalSales;
		this.productTotalSalesTaxExcl = productTotalSalesTaxExcl;
		this.taxValue = taxValue;
		this.totalCostPrice = costPrice;
	}
	
	public Float getProductCostPrice() {
		return productCostPrice;
	}

	public void setProductCostPrice(Float productCostPrice) {
		this.productCostPrice = productCostPrice;
	}
	
	public PrestashopOrderProducts (String name) {
		this.productName = name;
	}
	
	public PrestashopOrderProducts() {
		
	}
	
	public Double getProductTotalSalesTaxExcl() {
		return productTotalSalesTaxExcl;
	}

	public void setProductTotalSalesTaxExcl(Double productTotalSalesTaxExcl) {
		this.productTotalSalesTaxExcl = productTotalSalesTaxExcl;
	}

	public Double getTaxValue() {
		return taxValue;
	}

	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}
	
	public Long getProductTotalQuantity() {
		return productTotalQuantity;
	}

	public void setProductTotalQuantity(Long productTotalQuantity) {
		this.productTotalQuantity = productTotalQuantity;
	}

	public Double getProductTotalSales() {
		return productTotalSales;
	}

	public void setProductTotalSales(Double productTotalSales) {
		this.productTotalSales = productTotalSales;
	}

	public Integer getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(Integer productDetailId) {
		this.productDetailId = productDetailId;
	}

	public Float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}

	public Float getProducdPriceTaxIncl() {
		return producdPriceTaxIncl;
	}

	public void setProducdPriceTaxIncl(Float producdPriceTaxIncl) {
		this.producdPriceTaxIncl = producdPriceTaxIncl;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public PrestashopOrders getOrder() {
		return order;
	}

	public void setOrder(PrestashopOrders order) {
		this.order = order;
	}
}
