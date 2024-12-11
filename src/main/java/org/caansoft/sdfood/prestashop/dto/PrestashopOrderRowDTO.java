package org.caansoft.sdfood.prestashop.dto;

import javax.xml.bind.annotation.XmlElement;

public class PrestashopOrderRowDTO {

	private Integer id;

	private Integer productId;

	private Integer productAttributeId;

	private Integer productQuantity;

	private String productName;

	private String productReference;

	private String productEan13;

	private String productIsBN;

	private String productUpc;

	private Float productPrice;

	private Integer idCustomization;

	private Float unitPriceTaxIncl;

	private Float unitPriceTaxExcl;
	
	private Float costPrice;
	
	private Boolean expiry;
	
	private String imageUrl;
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getExpiry() {
		return expiry;
	}

	public void setExpiry(Boolean expiry) {
		this.expiry = expiry;
	}

	public Boolean getLotNumber() {
		return lotNumber;
	}

	public void setLotNumber(Boolean lotNumber) {
		this.lotNumber = lotNumber;
	}

	private Boolean lotNumber;
	
	private Long systemProductId;

	public Long getSystemProductId() {
		return systemProductId;
	}

	public void setSystemProductId(Long systemProductId) {
		this.systemProductId = systemProductId;
	}

	public Float getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Float costPrice) {
		this.costPrice = costPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement(name = "product_id")
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@XmlElement(name = "product_attribute_id")
	public Integer getProductAttributeId() {
		return productAttributeId;
	}

	public void setProductAttributeId(Integer productAttributeId) {
		this.productAttributeId = productAttributeId;
	}

	@XmlElement(name = "product_quantity")
	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	@XmlElement(name = "product_name")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@XmlElement(name = "product_reference")
	public String getProductReference() {
		return productReference;
	}

	public void setProductReference(String productReference) {
		this.productReference = productReference;
	}

	@XmlElement(name = "product_ean13")
	public String getProductEan13() {
		return productEan13;
	}

	public void setProductEan13(String productEan13) {
		this.productEan13 = productEan13;
	}

	@XmlElement(name = "product_isbn")
	public String getProductIsBN() {
		return productIsBN;
	}

	public void setProductIsBN(String productIsBN) {
		this.productIsBN = productIsBN;
	}

	@XmlElement(name = "product_upc")
	public String getProductUpc() {
		return productUpc;
	}

	public void setProductUpc(String productUpc) {
		this.productUpc = productUpc;
	}

	@XmlElement(name = "product_price")
	public Float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}

	@XmlElement(name = "id_customization")
	public Integer getIdCustomization() {
		return idCustomization;
	}

	public void setIdCustomization(Integer idCustomization) {
		this.idCustomization = idCustomization;
	}

	@XmlElement(name = "unit_price_tax_incl")
	public Float getUnitPriceTaxIncl() {
		return unitPriceTaxIncl;
	}

	public void setUnitPriceTaxIncl(Float unitPriceTaxIncl) {
		this.unitPriceTaxIncl = unitPriceTaxIncl;
	}

	@XmlElement(name = "unit_price_tax_excl")
	public Float getUnitPriceTaxExcl() {
		return unitPriceTaxExcl;
	}

	public void setUnitPriceTaxExcl(Float unitPriceTaxExcl) {
		this.unitPriceTaxExcl = unitPriceTaxExcl;
	}

}
