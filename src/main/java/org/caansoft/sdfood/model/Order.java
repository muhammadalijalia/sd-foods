package org.caansoft.sdfood.model;

import org.caansoft.sdfood.enums.OrderStatus;
import org.caansoft.core.model.BaseEntity;
import org.caansoft.core.model.Employee;
import org.caansoft.core.model.Media;
import org.caansoft.core.model.Partner;
import org.caansoft.core.model.Team;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name ="orders")
public class Order extends BaseEntity {

	private String number;
	private String invoiceNumber;

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<OrderProduct> orderProducts;

	private String orderName;

	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;

	@OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<OrderPhoto> orderPhotoList;

	//    @JsonBackReference
	//	@ManyToMany(cascade = CascadeType.ALL)
	//    @JoinTable
	//    private List<Product> product;

	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Timestamp startTime;

	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Timestamp deliveryDate;
	private Timestamp deliveryTime;

	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Timestamp expectedDeliveryDate;
	private Timestamp expectedDeliveryTime;

	private Float totalAmount;
	private Float discountPercentage;

	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Timestamp quotationDate;

	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Timestamp quotationValidityDate;

	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Timestamp invoiceDate;

	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
	private Timestamp invoiceExpiryDate;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "signed_contract_media_id")
	private Media signedContractMedia;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "signed_invoice_media_id")
	private Media signedInvoiceMedia;

	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "purchase_order_media_id")
	private Media purchaseOrderMedia;

	@OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<OrderStatusHistory> statusHistoryList;

	@ManyToOne
	@JoinColumn(name = "undertaken_by")
	private Employee undertakenBy;
	private Boolean quotation;
	private Boolean purchaseOrder;
	private Boolean frozen;
	private String logisticsType;
	private String transporterName;
	private Boolean stockRotated;
	private String reason;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

//	private Float employeeCommission;

//	@ManyToOne
//	@JoinColumn(name = "supervisor_id")
//	private Employee supervisor;

	@ManyToOne
	@JoinColumn(name = "team_id")
	private Team team;

	@ManyToOne
	private Partner partners;

	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Timestamp deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Timestamp getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Partner getPartners() {
		return partners;
	}

	public void setPartners(Partner partners) {
		this.partners = partners;
	}

	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
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

	public List<OrderPhoto> getOrderPhotoList() {
		return orderPhotoList;
	}

	public void setOrderPhotoList(List<OrderPhoto> orderPhotoList) {
		this.orderPhotoList = orderPhotoList;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
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

	public Media getSignedContractMedia() {
		return signedContractMedia;
	}

	public void setSignedContractMedia(Media signedContractMedia) {
		this.signedContractMedia = signedContractMedia;
	}

	public Media getSignedInvoiceMedia() {
		return signedInvoiceMedia;
	}

	public void setSignedInvoiceMedia(Media signedInvoiceMedia) {
		this.signedInvoiceMedia = signedInvoiceMedia;
	}

	public Media getPurchaseOrderMedia() {
		return purchaseOrderMedia;
	}

	public void setPurchaseOrderMedia(Media purchaseOrderMedia) {
		this.purchaseOrderMedia = purchaseOrderMedia;
	}

	public List<OrderStatusHistory> getStatusHistoryList() {
		return statusHistoryList;
	}

	public void setStatusHistoryList(List<OrderStatusHistory> statusHistoryList) {
		this.statusHistoryList = statusHistoryList;
	}

	public Employee getUndertakenBy() {
		return undertakenBy;
	}

	public void setUndertakenBy(Employee undertakenBy) {
		this.undertakenBy = undertakenBy;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

//	public Float getEmployeeCommission() {
//		return employeeCommission;
//	}
//
//	public void setEmployeeCommission(Float employeeCommission) {
//		this.employeeCommission = employeeCommission;
//	}

//	public Employee getSupervisor() {
//		return supervisor;
//	}
//
//	public void setSupervisor(Employee supervisor) {
//		this.supervisor = supervisor;
//	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Timestamp getExpectedDeliveryDate() {
		return expectedDeliveryDate;
	}

	public void setExpectedDeliveryDate(Timestamp expectedDeliveryDate) {
		this.expectedDeliveryDate = expectedDeliveryDate;
	}

	public Timestamp getExpectedDeliveryTime() {
		return expectedDeliveryTime;
	}

	public void setExpectedDeliveryTime(Timestamp expectedDeliveryTime) {
		this.expectedDeliveryTime = expectedDeliveryTime;
	}
}
