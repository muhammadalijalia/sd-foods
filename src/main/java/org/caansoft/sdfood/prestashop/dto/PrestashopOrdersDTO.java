package org.caansoft.sdfood.prestashop.dto;

import javax.xml.bind.annotation.XmlElement;

import org.caansoft.core.dto.EmployeeDto;
import org.caansoft.core.model.Employee;


public class PrestashopOrdersDTO {

	private Integer id;

	private Integer idAddressDelivery;

	private Integer idAddressInvoice;

	private Integer idCart;

	private Integer idCurrency;

	private Integer idLang;

	private Integer idCustomer;

	private Integer idCarrier;

	private String currentState;

	private String module;

	private Integer invoiceNumber;

	private Long invoicedate;

	private Integer deliveryNumber;

	private String deliveryDate;

	private Integer valid;

	private String dateAdd;

	private String dateUpd;

	private Integer shippingNumber;

	private Integer idShopGroup;

	private Integer idShop;

	private String secureKey;

	private String payment;

	private Boolean recyclable;

	private Boolean gift;

	private String giftMessage;

	private Boolean mobileTheme;

	private Float totalDiscounts;

	private Float totalDiscountsTaxIncl;

	private Float totalDiscountsTaxExcl;

	private Float totalPaid;

	private Float totalPaidTaxIncl;

	private Float totalPaidTaxExcl;

	private Float totalPaidReal;

	private Float totalProducts;

	private Float totalProductsWt;

	private Float totalShipping;

	private Float totalShippingTaxIncl;

	private Float totalShippingTaxExcl;

	private Float carrierTaxRate;

	private Float totalWrapping;

	private Float totalWrappingTaxIncl;

	private Float totalWrappingTaxExcl;

	private Integer roundMode;

	private Integer roundType;

	private Float conversionRate;

	private String reference;
	
	private String processed;
	
	private String clientName;
	
	private String clientCompany;
	
	private String updatedBy;
	
	private String updatedDate;	
	
	private EmployeeDto rider;

	public EmployeeDto getRider() {
		return rider;
	}

	public void setRider(EmployeeDto rider) {
		this.rider = rider;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getClientCompany() {
		return clientCompany;
	}

	public void setClientCompany(String clientCompany) {
		this.clientCompany = clientCompany;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String string) {
		this.processed = string;
	}

	private PrestashopOrderAssociationDTO prestashopOrderAssociationDTO;

	@XmlElement(name="id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlElement(name = "id_address_delivery")
	public Integer getIdAddressDelivery() {
		return idAddressDelivery;
	}

	public void setIdAddressDelivery(Integer idAddressDelivery) {
		this.idAddressDelivery = idAddressDelivery;
	}

	@XmlElement(name = "id_address_invoice")
	public Integer getIdAddressInvoice() {
		return idAddressInvoice;
	}

	public void setIdAddressInvoice(Integer idAddressInvoice) {
		this.idAddressInvoice = idAddressInvoice;
	}

	@XmlElement(name = "id_cart")
	public Integer getIdCart() {
		return idCart;
	}

	public void setIdCart(Integer idCart) {
		this.idCart = idCart;
	}

	@XmlElement(name = "id_currency")
	public Integer getIdCurrency() {
		return idCurrency;
	}

	public void setIdCurrency(Integer idCurrency) {
		this.idCurrency = idCurrency;
	}

	@XmlElement(name = "id_lang")
	public Integer getIdLang() {
		return idLang;
	}

	public void setIdLang(Integer idLang) {
		this.idLang = idLang;
	}

	@XmlElement(name = "id_customer")
	public Integer getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}

	@XmlElement(name = "id_carrier")
	public Integer getIdCarrier() {
		return idCarrier;
	}

	public void setIdCarrier(Integer idCarrier) {
		this.idCarrier = idCarrier;
	}

	@XmlElement(name = "current_state")
	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	@XmlElement(name = "module")
	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@XmlElement(name = "invoice_number")
	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoceNumber) {
		this.invoiceNumber = invoceNumber;
	}

	@XmlElement(name = "invoice_date")
	public Long getInvoicedate() {
		return invoicedate;
	}

	public void setInvoicedate(Long invoicedate) {
		this.invoicedate = invoicedate;
	}

	@XmlElement(name = "delivery_number")
	public Integer getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(Integer deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	@XmlElement(name = "delivery_date")
	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	@XmlElement(name = "valid")
	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	@XmlElement(name = "date_add")
	public String getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(String dateAdd) {
		this.dateAdd = dateAdd;
	}

	@XmlElement(name = "date_upd")
	public String getDateUpd() {
		return dateUpd;
	}

	public void setDateUpd(String dateUpd) {
		this.dateUpd = dateUpd;
	}

	@XmlElement(name = "shipping_number")
	public Integer getShippingNumber() {
		return shippingNumber;
	}

	public void setShippingNumber(Integer shippingNumber) {
		this.shippingNumber = shippingNumber;
	}

	@XmlElement(name = "id_shop_group")
	public Integer getIdShopGroup() {
		return idShopGroup;
	}

	public void setIdShopGroup(Integer idShopGroup) {
		this.idShopGroup = idShopGroup;
	}

	@XmlElement(name = "id_shop")
	public Integer getIdShop() {
		return idShop;
	}

	public void setIdShop(Integer idShop) {
		this.idShop = idShop;
	}

	@XmlElement(name = "secure_key")
	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}

	@XmlElement(name = "payment")
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@XmlElement(name = "recylable")
	public Boolean getRecyclable() {
		return recyclable;
	}

	public void setRecyclable(Boolean recyclable) {
		this.recyclable = recyclable;
	}

	@XmlElement(name = "gift")
	public Boolean getGift() {
		return gift;
	}

	public void setGift(Boolean gift) {
		this.gift = gift;
	}

	@XmlElement(name = "gift_message")
	public String getGiftMessage() {
		return giftMessage;
	}

	public void setGiftMessage(String giftMessage) {
		this.giftMessage = giftMessage;
	}

	@XmlElement(name = "mobile_theme")
	public Boolean getMobileTheme() {
		return mobileTheme;
	}

	public void setMobileTheme(Boolean mobileTheme) {
		this.mobileTheme = mobileTheme;
	}

	@XmlElement(name = "total_discounts")
	public Float getTotalDiscounts() {
		return totalDiscounts;
	}

	public void setTotalDiscounts(Float totalDiscounts) {
		this.totalDiscounts = totalDiscounts;
	}

	@XmlElement(name = "total_discounts_tax_incl")
	public Float getTotalDiscountsTaxIncl() {
		return totalDiscountsTaxIncl;
	}

	public void setTotalDiscountsTaxIncl(Float totalDiscountsTaxIncl) {
		this.totalDiscountsTaxIncl = totalDiscountsTaxIncl;
	}

	@XmlElement(name = "total_discounts_tax_excl")
	public Float getTotalDiscountsTaxExcl() {
		return totalDiscountsTaxExcl;
	}

	public void setTotalDiscountsTaxExcl(Float totalDiscountsTaxExcl) {
		this.totalDiscountsTaxExcl = totalDiscountsTaxExcl;
	}

	@XmlElement(name = "total_paid")
	public Float getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(Float totalPaid) {
		this.totalPaid = totalPaid;
	}

	@XmlElement(name = "total_paid_tax_incl")
	public Float getTotalPaidTaxIncl() {
		return totalPaidTaxIncl;
	}

	public void setTotalPaidTaxIncl(Float totalPaidTaxIncl) {
		this.totalPaidTaxIncl = totalPaidTaxIncl;
	}

	@XmlElement(name = "total_paid_tax_excl")
	public Float getTotalPaidTaxExcl() {
		return totalPaidTaxExcl;
	}

	public void setTotalPaidTaxExcl(Float totalPaidTaxExcl) {
		this.totalPaidTaxExcl = totalPaidTaxExcl;
	}

	@XmlElement(name = "total_paid_real")
	public Float getTotalPaidReal() {
		return totalPaidReal;
	}

	public void setTotalPaidReal(Float totalPaidReal) {
		this.totalPaidReal = totalPaidReal;
	}

	@XmlElement(name = "total_products")
	public Float getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(Float totalProducts) {
		this.totalProducts = totalProducts;
	}

	@XmlElement(name = "total_products_wt")
	public Float getTotalProductsWt() {
		return totalProductsWt;
	}

	public void setTotalProductsWt(Float totalProductsWt) {
		this.totalProductsWt = totalProductsWt;
	}

	@XmlElement(name = "total_shipping")
	public Float getTotalShipping() {
		return totalShipping;
	}

	public void setTotalShipping(Float totalShipping) {
		this.totalShipping = totalShipping;
	}

	@XmlElement(name = "total_shipping_tax_incl")
	public Float getTotalShippingTaxIncl() {
		return totalShippingTaxIncl;
	}

	public void setTotalShippingTaxIncl(Float totalShippingTaxIncl) {
		this.totalShippingTaxIncl = totalShippingTaxIncl;
	}

	@XmlElement(name = "total_shipping_tax_excl")
	public Float getTotalShippingTaxExcl() {
		return totalShippingTaxExcl;
	}

	public void setTotalShippingTaxExcl(Float totalShippingTaxExcl) {
		this.totalShippingTaxExcl = totalShippingTaxExcl;
	}

	@XmlElement(name = "carrier_tax_rate")
	public Float getCarrierTaxRate() {
		return carrierTaxRate;
	}

	public void setCarrierTaxRate(Float carrierTaxRate) {
		this.carrierTaxRate = carrierTaxRate;
	}

	@XmlElement(name = "total_wrapping")
	public Float getTotalWrapping() {
		return totalWrapping;
	}

	public void setTotalWrapping(Float totalWrapping) {
		this.totalWrapping = totalWrapping;
	}

	@XmlElement(name = "total_wrapping_tax_incl")
	public Float getTotalWrappingTaxIncl() {
		return totalWrappingTaxIncl;
	}

	public void setTotalWrappingTaxIncl(Float totalWrappingTaxIncl) {
		this.totalWrappingTaxIncl = totalWrappingTaxIncl;
	}

	@XmlElement(name = "total_wrapping_tax_excl")
	public Float getTotalWrappingTaxExcl() {
		return totalWrappingTaxExcl;
	}

	public void setTotalWrappingTaxExcl(Float totalWrappingTaxExcl) {
		this.totalWrappingTaxExcl = totalWrappingTaxExcl;
	}

	@XmlElement(name = "round_mode")
	public Integer getRoundMode() {
		return roundMode;
	}

	public void setRoundMode(Integer roundMode) {
		this.roundMode = roundMode;
	}

	@XmlElement(name = "round_type")
	public Integer getRoundType() {
		return roundType;
	}

	public void setRoundType(Integer roundType) {
		this.roundType = roundType;
	}

	@XmlElement(name = "conversion_rate")
	public Float getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(Float conversionRate) {
		this.conversionRate = conversionRate;
	}

	@XmlElement(name = "reference")
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@XmlElement(name = "associations")
	public PrestashopOrderAssociationDTO getPrestashopOrderAssociationDTO() {
		return prestashopOrderAssociationDTO;
	}

	public void setPrestashopOrderAssociationDTO(PrestashopOrderAssociationDTO prestashopOrderAssociationDTO) {
		this.prestashopOrderAssociationDTO = prestashopOrderAssociationDTO;
	}

}
