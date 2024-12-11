package org.caansoft.sdfood.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.caansoft.sdfood.enums.OrderStatus;
import org.caansoft.core.dto.BaseDto;
import org.caansoft.core.dto.EmployeeDto;
import org.caansoft.core.dto.MediaDto;
import org.caansoft.core.dto.PartnerDto;

import java.sql.Timestamp;
import java.util.List;

public class OrderDto extends BaseDto {

	private String number;
	private String invoiceNumber;
	private OrderStatus status;

	private String action;
	@JsonProperty(value = "photos")
	private List<OrderMediaDto> orderPhotos;
	private Timestamp startTime;
	private Timestamp deliveryDate;
	private Long deliveryTime;
	private Timestamp expectedDeliveryDate;
	private Long expectedDeliveryTime;
	@JsonProperty(value = "products")
	private List<ProductDto> productList;
	private Timestamp quotationDate;
	private Timestamp quotationValidityDate;
	private Timestamp invoiceDate;
	private Timestamp invoiceExpiryDate;

	private Float totalAmount;
	private Float discountPercentage;
	private Float discountedAmount;
	@JsonProperty(value = "signedContractMedia")
	private MediaDto signedContractMediaDto;
	@JsonProperty(value = "signedInvoiceMedia")
	private MediaDto signedInvoiceMediaDto;
	@JsonProperty(value = "purchaseOrderMedia")
	private MediaDto purchaseOrderMediaDto;
	private Boolean undertaken;
	private Boolean quotation;
	private Boolean purchaseOrder;
//	private Long supervisorId;
	private String supervisorName;
	private String comments;
	@JsonProperty(value = "employee")
	private EmployeeDto employeeDto;
	private Boolean frozen;
	private String logisticsType;
	private String transporterName;
	private Boolean stockRotated;
	private String reason;
	@JsonProperty(value = "statusHistoryList")
	private List<StatusHistoryDto> statusHistoryDtoList;

	@JsonProperty(value = "partner" , access = JsonProperty.Access.READ_WRITE)
	private PartnerDto partnerDto;

	
	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Timestamp deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public PartnerDto getPartnerDto() {
		return partnerDto;
	}

	public void setPartnerDto(PartnerDto partnerDto) {
		this.partnerDto = partnerDto;
	}

	public Boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(Boolean frozen) {
		this.frozen = frozen;
	}

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public String getTransporterName() {
		return transporterName;
	}

	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}

	public Boolean getStockRotated() {
		return stockRotated;
	}

	public void setStockRotated(Boolean stockRotated) {
		this.stockRotated = stockRotated;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<StatusHistoryDto> getStatusHistoryDtoList() {
		return statusHistoryDtoList;
	}

	public void setStatusHistoryDtoList(List<StatusHistoryDto> statusHistoryDtoList) {
		this.statusHistoryDtoList = statusHistoryDtoList;
	}

	public List<ProductDto> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDto> productList) {
		this.productList = productList;
	}
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<OrderMediaDto> getOrderPhotos() {
		return orderPhotos;
	}

	public void setOrderPhotos(List<OrderMediaDto> orderPhotos) {
		this.orderPhotos = orderPhotos;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getQuotationDate() {
		return quotationDate;
	}

	public void setQuotationDate(Timestamp quotationDate) {
		this.quotationDate = quotationDate;
	}

	public Timestamp getQuotationValidityDate() {
		return quotationValidityDate;
	}

	public void setQuotationValidityDate(Timestamp quotationValidityDate) {
		this.quotationValidityDate = quotationValidityDate;
	}

	public Timestamp getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Timestamp invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Timestamp getInvoiceExpiryDate() {
		return invoiceExpiryDate;
	}

	public void setInvoiceExpiryDate(Timestamp invoiceExpiryDate) {
		this.invoiceExpiryDate = invoiceExpiryDate;
	}

	public Float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Float getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Float discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Float getDiscountedAmount() {
		return discountedAmount;
	}

	public void setDiscountedAmount(Float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}

	public MediaDto getSignedContractMediaDto() {
		return signedContractMediaDto;
	}

	public void setSignedContractMediaDto(MediaDto signedContractMediaDto) {
		this.signedContractMediaDto = signedContractMediaDto;
	}

	public MediaDto getSignedInvoiceMediaDto() {
		return signedInvoiceMediaDto;
	}

	public void setSignedInvoiceMediaDto(MediaDto signedInvoiceMediaDto) {
		this.signedInvoiceMediaDto = signedInvoiceMediaDto;
	}

	public MediaDto getPurchaseOrderMediaDto() {
		return purchaseOrderMediaDto;
	}

	public void setPurchaseOrderMediaDto(MediaDto purchaseOrderMediaDto) {
		this.purchaseOrderMediaDto = purchaseOrderMediaDto;
	}

	public Boolean getUndertaken() {
		return undertaken;
	}

	public void setUndertaken(Boolean undertaken) {
		this.undertaken = undertaken;
	}

	public Boolean getQuotation() {
		return quotation;
	}

	public void setQuotation(Boolean quotation) {
		this.quotation = quotation;
	}

	public Boolean getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(Boolean purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

//	public Long getSupervisorId() {
//		return supervisorId;
//	}
//
//	public void setSupervisorId(Long supervisorId) {
//		this.supervisorId = supervisorId;
//	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public EmployeeDto getEmployeeDto() {
		return employeeDto;
	}

	public void setEmployeeDto(EmployeeDto employeeDto) {
		this.employeeDto = employeeDto;
	}


	public Timestamp getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Timestamp expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}
	public String getAction() { return action; }
	public void setAction(String action) { this.action = action; }

	public Long getExpectedDeliveryTime() {
		return expectedDeliveryTime;
	}

	public void setExpectedDeliveryTime(Long expectedDeliveryTime) {
		this.expectedDeliveryTime = expectedDeliveryTime;
	}
}

