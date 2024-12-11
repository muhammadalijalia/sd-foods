package org.caansoft.sdfood.prestashop.dto;

import javax.xml.bind.annotation.XmlElement;

public class PrestashopOrderDetailsDTO {

	private Integer id;
	private Integer idOrder;
	private Integer productId;
	private Integer productAttributeId;
	private Integer productQuantityReinjected;
	private Float groupReduction;
	private Integer discountQuantityApplied;
	private String downloadHash;
	private Long downloadDeadline;
	private Integer idOrderInvoice;
	private Integer idWarehouse;
	private Integer idShop;
	private Integer idCustomization;
	private String productName;
	private Integer productQuantity;
	private Integer productQuantityInStock;
	private Integer productQuantityReturn;
	private Integer productQuantityRefunded;
	private Float productPrice;
	private Float reductionPercent;
	private Float reductionAmount;
	private Float reductionAmountTaxIncl;
	private Float reductionAmountTaxExcl;
	private Float productQuantityDiscount;
	private String productEan13;
	private String productIsBn;
	private String productUpc;
	private String productMpn;
	private String productReference;
	private String productSupplierReference;
	private Float productWeight;
	private Integer taxComputationMethod;
	private Integer idTaxRulesGroup;
	private Float ecoTax;
	private Float ecoTaxRate;
	private Integer donwloadNb;
	private Float unitPriceTaxIncl;
	private Float unitPriceTaxEncl;
	private Float totalPriceTaxIncl;
	private Float totalPriceTaxEncl;
	private Float totalShippingPriceTaxIncl;
	private Float totalShippingPriceTaxEncl;
	private Float purchaseSupplierPrice;
	private Float originalProductPrice;
	private Float originalWholesalePrice;
	private Float totalRefundedTaxExcl;
	private Float totalRefundedTaxIxcl;

	@XmlElement(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement(name = "id_order")
	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
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

	@XmlElement(name = "product_quantity_reinjected")
	public Integer getProductQuantityReinjected() {
		return productQuantityReinjected;
	}

	public void setProductQuantityReinjected(Integer productQuantityReinjected) {
		this.productQuantityReinjected = productQuantityReinjected;
	}

	@XmlElement(name = "group_reduction")
	public Float getGroupReduction() {
		return groupReduction;
	}

	public void setGroupReduction(Float groupReduction) {
		this.groupReduction = groupReduction;
	}

	@XmlElement(name = "discount_quantity_applied")
	public Integer getDiscountQuantityApplied() {
		return discountQuantityApplied;
	}

	public void setDiscountQuantityApplied(Integer discountQuantityApplied) {
		this.discountQuantityApplied = discountQuantityApplied;
	}

	@XmlElement(name = "download_hash")
	public String getDownloadHash() {
		return downloadHash;
	}

	public void setDownloadHash(String downloadHash) {
		this.downloadHash = downloadHash;
	}

	@XmlElement(name = "download_deadline")
	public Long getDownloadDeadline() {
		return downloadDeadline;
	}

	public void setDownloadDeadline(Long downloadDeadline) {
		this.downloadDeadline = downloadDeadline;
	}

	@XmlElement(name = "id_order_invoice")
	public Integer getIdOrderInvoice() {
		return idOrderInvoice;
	}

	public void setIdOrderInvoice(Integer idOrderInvoice) {
		this.idOrderInvoice = idOrderInvoice;
	}

	@XmlElement(name = "id_warehouse")
	public Integer getIdWarehouse() {
		return idWarehouse;
	}

	public void setIdWarehouse(Integer idWarehouse) {
		this.idWarehouse = idWarehouse;
	}

	@XmlElement(name = "id_shop")
	public Integer getIdShop() {
		return idShop;
	}

	public void setIdShop(Integer idShop) {
		this.idShop = idShop;
	}

	@XmlElement(name = "id_customization")
	public Integer getIdCustomization() {
		return idCustomization;
	}

	public void setIdCustomization(Integer idCustomization) {
		this.idCustomization = idCustomization;
	}

	@XmlElement(name = "product_name")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@XmlElement(name = "product_quantity")
	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	@XmlElement(name = "product_quantity_in_stock")
	public Integer getProductQuantityInStock() {
		return productQuantityInStock;
	}

	public void setProductQuantityInStock(Integer productQuantityInStock) {
		this.productQuantityInStock = productQuantityInStock;
	}

	@XmlElement(name = "product_quantity_return")
	public Integer getProductQuantityReturn() {
		return productQuantityReturn;
	}

	public void setProductQuantityReturn(Integer productQuantityReturn) {
		this.productQuantityReturn = productQuantityReturn;
	}

	@XmlElement(name = "product_quantity_refunded")
	public Integer getProductQuantityRefunded() {
		return productQuantityRefunded;
	}

	public void setProductQuantityRefunded(Integer productQuantityRefunded) {
		this.productQuantityRefunded = productQuantityRefunded;
	}

	@XmlElement(name = "product_price")
	public Float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Float productPrice) {
		this.productPrice = productPrice;
	}

	@XmlElement(name = "reduction_percent")
	public Float getReductionPercent() {
		return reductionPercent;
	}

	public void setReductionPercent(Float reductionPercent) {
		this.reductionPercent = reductionPercent;
	}

	@XmlElement(name = "reduction_amount")
	public Float getReductionAmount() {
		return reductionAmount;
	}

	public void setReductionAmount(Float reductionAmount) {
		this.reductionAmount = reductionAmount;
	}

	@XmlElement(name = "reduction_amount_tax_incl")
	public Float getReductionAmountTaxIncl() {
		return reductionAmountTaxIncl;
	}

	public void setReductionAmountTaxIncl(Float reductionAmountTaxIncl) {
		this.reductionAmountTaxIncl = reductionAmountTaxIncl;
	}

	@XmlElement(name = "reduction_amount_tax_excl")
	public Float getReductionAmountTaxExcl() {
		return reductionAmountTaxExcl;
	}

	public void setReductionAmountTaxExcl(Float reductionAmountTaxExcl) {
		this.reductionAmountTaxExcl = reductionAmountTaxExcl;
	}

	@XmlElement(name = "product_quantity_discount")
	public Float getProductQuantityDiscount() {
		return productQuantityDiscount;
	}

	public void setProductQuantityDiscount(Float productQuantityDiscount) {
		this.productQuantityDiscount = productQuantityDiscount;
	}

	@XmlElement(name = "product_ean13")
	public String getProductEan13() {
		return productEan13;
	}

	public void setProductEan13(String productEan13) {
		this.productEan13 = productEan13;
	}

	@XmlElement(name = "product_isbn")
	public String getProductIsBn() {
		return productIsBn;
	}

	public void setProductIsBn(String productIsBn) {
		this.productIsBn = productIsBn;
	}

	@XmlElement(name = "product_upc")
	public String getProductUpc() {
		return productUpc;
	}

	public void setProductUpc(String productUpc) {
		this.productUpc = productUpc;
	}

	@XmlElement(name = "product_mpn")
	public String getProductMpn() {
		return productMpn;
	}

	public void setProductMpn(String productMpn) {
		this.productMpn = productMpn;
	}

	@XmlElement(name = "product_reference")
	public String getProductReference() {
		return productReference;
	}

	public void setProductReference(String productReference) {
		this.productReference = productReference;
	}

	@XmlElement(name = "product_supplier_reference")
	public String getProductSupplierReference() {
		return productSupplierReference;
	}

	public void setProductSupplierReference(String productSupplierReference) {
		this.productSupplierReference = productSupplierReference;
	}

	@XmlElement(name = "product_weight")
	public Float getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(Float productWeight) {
		this.productWeight = productWeight;
	}

	@XmlElement(name = "tax_computation_method")
	public Integer getTaxComputationMethod() {
		return taxComputationMethod;
	}

	public void setTaxComputationMethod(Integer taxComputationMethod) {
		this.taxComputationMethod = taxComputationMethod;
	}

	@XmlElement(name = "id_tax_rules_group")
	public Integer getIdTaxRulesGroup() {
		return idTaxRulesGroup;
	}

	public void setIdTaxRulesGroup(Integer idTaxRulesGroup) {
		this.idTaxRulesGroup = idTaxRulesGroup;
	}

	@XmlElement(name = "ecotax")
	public Float getEcoTax() {
		return ecoTax;
	}

	public void setEcoTax(Float ecoTax) {
		this.ecoTax = ecoTax;
	}

	@XmlElement(name = "ecotax_tax_rate")
	public Float getEcoTaxRate() {
		return ecoTaxRate;
	}

	public void setEcoTaxRate(Float ecoTaxRate) {
		this.ecoTaxRate = ecoTaxRate;
	}

	@XmlElement(name = "download_nb")
	public Integer getDonwloadNb() {
		return donwloadNb;
	}

	public void setDonwloadNb(Integer donwloadNb) {
		this.donwloadNb = donwloadNb;
	}

	@XmlElement(name = "unit_price_tax_incl")
	public Float getUnitPriceTaxIncl() {
		return unitPriceTaxIncl;
	}

	public void setUnitPriceTaxIncl(Float unitPriceTaxIncl) {
		this.unitPriceTaxIncl = unitPriceTaxIncl;
	}

	@XmlElement(name = "unit_price_tax_excl")
	public Float getUnitPriceTaxEncl() {
		return unitPriceTaxEncl;
	}

	public void setUnitPriceTaxEncl(Float unitPriceTaxEncl) {
		this.unitPriceTaxEncl = unitPriceTaxEncl;
	}

	@XmlElement(name = "total_price_tax_incl")
	public Float getTotalPriceTaxIncl() {
		return totalPriceTaxIncl;
	}

	public void setTotalPriceTaxIncl(Float totalPriceTaxIncl) {
		this.totalPriceTaxIncl = totalPriceTaxIncl;
	}

	@XmlElement(name = "total_price_tax_excl")
	public Float getTotalPriceTaxEncl() {
		return totalPriceTaxEncl;
	}

	public void setTotalPriceTaxEncl(Float totalPriceTaxEncl) {
		this.totalPriceTaxEncl = totalPriceTaxEncl;
	}

	@XmlElement(name = "total_shipping_price_tax_incl")
	public Float getTotalShippingPriceTaxIncl() {
		return totalShippingPriceTaxIncl;
	}

	public void setTotalShippingPriceTaxIncl(Float totalShippingPriceTaxIncl) {
		this.totalShippingPriceTaxIncl = totalShippingPriceTaxIncl;
	}

	@XmlElement(name = "total_shipping_price_tax_excl")
	public Float getTotalShippingPriceTaxEncl() {
		return totalShippingPriceTaxEncl;
	}

	public void setTotalShippingPriceTaxEncl(Float totalShippingPriceTaxEncl) {
		this.totalShippingPriceTaxEncl = totalShippingPriceTaxEncl;
	}

	@XmlElement(name = "purchase_supplier_price")
	public Float getPurchaseSupplierPrice() {
		return purchaseSupplierPrice;
	}

	public void setPurchaseSupplierPrice(Float purchaseSupplierPrice) {
		this.purchaseSupplierPrice = purchaseSupplierPrice;
	}

	@XmlElement(name = "original_product_price")
	public Float getOriginalProductPrice() {
		return originalProductPrice;
	}

	public void setOriginalProductPrice(Float originalProductPrice) {
		this.originalProductPrice = originalProductPrice;
	}

	@XmlElement(name = "original_wholesale_price")
	public Float getOriginalWholesalePrice() {
		return originalWholesalePrice;
	}

	public void setOriginalWholesalePrice(Float originalWholesalePrice) {
		this.originalWholesalePrice = originalWholesalePrice;
	}

	@XmlElement(name = "total_refunded_tax_excl")
	public Float getTotalRefundedTaxExcl() {
		return totalRefundedTaxExcl;
	}

	public void setTotalRefundedTaxExcl(Float totalRefundedTaxExcl) {
		this.totalRefundedTaxExcl = totalRefundedTaxExcl;
	}

	@XmlElement(name = "total_refunded_tax_incl")
	public Float getTotalRefundedTaxIxcl() {
		return totalRefundedTaxIxcl;
	}

	public void setTotalRefundedTaxIxcl(Float totalRefundedTaxIxcl) {
		this.totalRefundedTaxIxcl = totalRefundedTaxIxcl;
	}
}
