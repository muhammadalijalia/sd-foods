package org.caansoft.sdfood.prestashop.dto;

public class PrestashopProductSalesDTO {

	private Integer id;
	private Integer productTotalQuantity;
	private String productTotalSales;
	private String productName;
	private String productTotalSalesTaxExcl;
	private String productTaxValue;
	private String productCostPrice;

	public String getProductCostPrice() {
		return productCostPrice;
	}

	public void setProductCostPrice(String productCostPrice) {
		this.productCostPrice = productCostPrice;
	}

	public String getProductTotalSalesTaxExcl() {
		return productTotalSalesTaxExcl;
	}

	public void setProductTotalSalesTaxExcl(String productTotalSalesTaxExcl) {
		this.productTotalSalesTaxExcl = productTotalSalesTaxExcl;
	}

	public String getProductTaxValue() {
		return productTaxValue;
	}

	public void setProductTaxValue(String productTaxValue) {
		this.productTaxValue = productTaxValue;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProductTotalQuantity() {
		return productTotalQuantity;
	}

	public void setProductTotalQuantity(Integer productTotalQuantity) {
		this.productTotalQuantity = productTotalQuantity;
	}

	public String getProductTotalSales() {
		return productTotalSales;
	}

	public void setProductTotalSales(String productTotalSales) {
		this.productTotalSales = productTotalSales;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
