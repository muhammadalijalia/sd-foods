package org.caansoft.sdfood.prestashop.dto;

import javax.xml.bind.annotation.XmlElement;

public class PrestashopStockMovementDto {

	private Integer id;
	private Integer idProduct;
	private Integer idProductAttribute;
	private Integer idWarehouse;
	private Integer idCurrency;
	private String managementType;
	private Integer idEmployee;
	private Integer idStock;
	private Integer idStockMvtReason;
	private Integer idOrder;
	private Integer idSupplyOrder;
	private String productName;
	private String ean13;
	private String reference;
	private String mpn;
	private Integer physicalQuantity;
	private Integer sign;
	private Float lastWa;
	private Float currentWa;
	private Float priceTe;
	private String dateAdd;
	
	@XmlElement(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement(name="id_product")
	public Integer getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}
	
	@XmlElement(name="id_product_attribute")
	public Integer getIdProductAttribute() {
		return idProductAttribute;
	}
	public void setIdProductAttribute(Integer idProductAttribute) {
		this.idProductAttribute = idProductAttribute;
	}
	
	@XmlElement(name="id_warehouse")
	public Integer getIdWarehouse() {
		return idWarehouse;
	}
	public void setIdWarehouse(Integer idWarehouse) {
		this.idWarehouse = idWarehouse;
	}
	
	@XmlElement(name="id_currency")
	public Integer getIdCurrency() {
		return idCurrency;
	}
	public void setIdCurrency(Integer idCurrency) {
		this.idCurrency = idCurrency;
	}
	
	@XmlElement(name="management_type")
	public String getManagementType() {
		return managementType;
	}
	public void setManagementType(String managementType) {
		this.managementType = managementType;
	}
	
	@XmlElement(name="id_employee")
	public Integer getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}
	
	@XmlElement(name="id_stock")
	public Integer getIdStock() {
		return idStock;
	}
	public void setIdStock(Integer idStock) {
		this.idStock = idStock;
	}
	
	@XmlElement(name="id_stock_mvt_reason")
	public Integer getIdStockMvtReason() {
		return idStockMvtReason;
	}
	public void setIdStockMvtReason(Integer idStockMvtReason) {
		this.idStockMvtReason = idStockMvtReason;
	}
	
	@XmlElement(name="id_order")
	public Integer getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}
	
	@XmlElement(name="id_supply_order")
	public Integer getIdSupplyOrder() {
		return idSupplyOrder;
	}
	public void setIdSupplyOrder(Integer idSupplyOrder) {
		this.idSupplyOrder = idSupplyOrder;
	}
	
	@XmlElement(name="product_name")
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@XmlElement(name="ean13")
	public String getEan13() {
		return ean13;
	}
	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}
	
	@XmlElement(name="reference")
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@XmlElement(name="mpn")
	public String getMpn() {
		return mpn;
	}
	public void setMpn(String mpn) {
		this.mpn = mpn;
	}
	
	@XmlElement(name="physical_quantity")
	public Integer getPhysicalQuantity() {
		return physicalQuantity;
	}
	public void setPhysicalQuantity(Integer physicalQuantity) {
		this.physicalQuantity = physicalQuantity;
	}
	
	@XmlElement(name="sign")
	public Integer getSign() {
		return sign;
	}
	public void setSign(Integer sign) {
		this.sign = sign;
	}
	
	@XmlElement(name="last_wa")
	public Float getLastWa() {
		return lastWa;
	}
	public void setLastWa(Float lastWa) {
		this.lastWa = lastWa;
	}
	
	@XmlElement(name="current_wa")
	public Float getCurrentWa() {
		return currentWa;
	}
	public void setCurrentWa(Float currentWa) {
		this.currentWa = currentWa;
	}
	
	@XmlElement(name="price_te")
	public Float getPriceTe() {
		return priceTe;
	}
	public void setPriceTe(Float priceTe) {
		this.priceTe = priceTe;
	}
	
	@XmlElement(name="date_add")
	public String getDateAdd() {
		return dateAdd;
	}
	public void setDateAdd(String dateAdd) {
		this.dateAdd = dateAdd;
	}
}
