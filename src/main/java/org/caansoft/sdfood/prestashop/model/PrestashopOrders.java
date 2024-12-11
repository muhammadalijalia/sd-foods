package org.caansoft.sdfood.prestashop.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.transaction.Transactional;

import org.caansoft.sdfood.enums.ScheduledJobUpdateStatus;
import org.caansoft.core.model.Employee;

@Transactional
@Entity
@Table(name = "prestashop_orders")
public class PrestashopOrders {

	@Id
	@Column(name = "prestashop_order_id")
	private Integer prestashopOrderId;

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval= true)
	private List<PrestashopOrderProducts> orderProducts;

	@Column(name = "order_current_status")
	private Integer orderCurrentState;

	@Column(name = "order_reference")
	private String reference;

	@Column(name = "order_delivery_date")
	private Timestamp deliveryDate;

	@Column(name = "date_added")
	private Timestamp dateOrderAdded;

	@Column(name = "date_updated")
	private Timestamp dateOrderUpdated;

	@Column(name = "order_total_price_tax_incl")
	private Float orderTotalPriceTaxIncl;

	@Column(name = "order_total_price_tax_excl")
	private Float orderTotalPriceTaxExcl;
	
	@Column
	private String processed;
	
	@Column
	private String updatedBy;
	
	@Column
	private Timestamp sdfoods_date_upd;
	
	@Column(name="address_id")
	private String addressId;
	
	@Enumerated(value = EnumType.STRING)
	private ScheduledJobUpdateStatus jobUpdateStatus;

	public ScheduledJobUpdateStatus getJobUpdateStatus() {
		return jobUpdateStatus;
	}

	public void setJobUpdateStatus(ScheduledJobUpdateStatus jobUpdateStatus) {
		this.jobUpdateStatus = jobUpdateStatus;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "rider_id")
	private Employee rider;

	public Employee getRider() {
		return rider;
	}

	public void setRider(Employee rider) {
		this.rider = rider;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public Timestamp getSdfoods_date_upd() {
		return sdfoods_date_upd;
	}

	public void setSdfoods_date_upd(Timestamp sdfoods_date_upd) {
		this.sdfoods_date_upd = sdfoods_date_upd;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", nullable = false)
	private PrestashopCustomer customer;

	public PrestashopCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(PrestashopCustomer customer) {
		this.customer = customer;
	}

	@Transient
	private Long totalOrders;
	
	@Transient
	private Double totalSalesAmount;
	
	@Transient
	private Date startDate;
	
	@Transient
	private Date endDate;
	
	public PrestashopOrders(Long orders, Double sales) {
		this.totalOrders = orders;
		this.totalSalesAmount = sales;
	}
	
	public PrestashopOrders(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public PrestashopOrders() {
		
	}

//	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
//	@JoinTable(name="prestashop_order_products",
//			joinColumns = @JoinColumn(name="prestashop_order_id"),
//			inverseJoinColumns = @JoinColumn(name="product_id"))
//	private List<PrestashopProducts> prestashopProducts;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(Long totalOrders) {
		this.totalOrders = totalOrders;
	}

	public Double getTotalSalesAmount() {
		return totalSalesAmount;
	}

	public void setTotalSalesAmount(Double totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}

	public List<PrestashopOrderProducts> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<PrestashopOrderProducts> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public Timestamp getDateOrderAdded() {
		return dateOrderAdded;
	}

	public void setDateOrderAdded(Timestamp dateOrderAdded) {
		this.dateOrderAdded = dateOrderAdded;
	}

	public Timestamp getDateOrderUpdated() {
		return dateOrderUpdated;
	}

	public void setDateOrderUpdated(Timestamp dateOrderUpdated) {
		this.dateOrderUpdated = dateOrderUpdated;
	}

	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Timestamp deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getOrderCurrentState() {
		return orderCurrentState;
	}

	public void setOrderCurrentState(Integer orderCurrentState) {
		this.orderCurrentState = orderCurrentState;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getPrestashopOrderId() {
		return prestashopOrderId;
	}

	public void setPrestashopOrderId(Integer prestashopOrderId) {
		this.prestashopOrderId = prestashopOrderId;
	}

	public Float getOrderTotalPriceTaxIncl() {
		return orderTotalPriceTaxIncl;
	}

	public void setOrderTotalPriceTaxIncl(Float orderTotalPriceTaxIncl) {
		this.orderTotalPriceTaxIncl = orderTotalPriceTaxIncl;
	}

	public Float getOrderTotalPriceTaxExcl() {
		return orderTotalPriceTaxExcl;
	}

	public void setOrderTotalPriceTaxExcl(Float orderTotalPriceTaxExcl) {
		this.orderTotalPriceTaxExcl = orderTotalPriceTaxExcl;
	}

//	public List<PrestashopProducts> getPrestashopProducts() {
//		return prestashopProducts;
//	}
//
//	public void setPrestashopProducts(List<PrestashopProducts> prestashopProducts) {
//		this.prestashopProducts = prestashopProducts;
//	}

}
