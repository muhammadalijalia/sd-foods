package org.caansoft.sdfood.model;

import org.caansoft.core.model.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "order_product_locations")
public class OrderProductLocation extends BaseEntity {	

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProducts;
    
    @Column(name="product_id")
    private Long productId ;
 
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	@Column(name = "received_quantity")
    private Float receivedQuantity;
    
    @Column(name = "damaged_quantity")
    private Float damagedQuantity;
    
    @Column(name = "lot_number")
    private String lotNumber;
    
    @Column(name="received_pallets")
    private Integer receivedPallets;
    
    @Column(name="received_number_of_units")
    private Integer receivedNumberofUnits;

	@Column(name="damage_number_of_units")
	private Integer damageNumberOfUnits;
    
	@DateTimeFormat(pattern = "yy-MM-dd HH:mm:ss")
    private Timestamp expiry;
	
	@Column(name="quantity_sold")
	private Long quantitySold;
	
	@Transient
	private Long queryProductId;
	
	@Transient
	private Long vendorId;
	
	@Transient
	private String productName;
	
	@Transient
	private Double totalOrderedPallets;
	
	@Transient
	private Long totalReceivedPallets;
	
	@Transient
	private Long totalOrderedNumberOfunits;
	
	@Transient
	private Double totalReceivedNumberOfUnits;
	
	@Transient
	private Long totalDamagedQuantity;
	
	@Transient
	private String vendorName;
	
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	
	public OrderProductLocation(Long queryProductId, Long vendorId, String productName, String vendorName, 
			Double totalOrderedPallets,
			Long totalReceivedPallets,
			Long totalOrderedNumberOfunits,
			Double totalReceivedNumberOfUnits,
			Long totalDamagedQuantity) {
		
		this.queryProductId = queryProductId;
		this.vendorId = vendorId;
		this.productName = productName;		
		this.vendorName = vendorName;
		this.totalOrderedPallets = totalOrderedPallets;
		this.totalReceivedPallets = totalReceivedPallets;
		this.totalOrderedNumberOfunits = totalOrderedNumberOfunits;
		this.totalReceivedNumberOfUnits = totalReceivedNumberOfUnits;
		this.totalDamagedQuantity = totalDamagedQuantity;
	}
	
	public Double getTotalOrderedPallets() {
		return totalOrderedPallets;
	}
	public void setTotalOrderedPallets(Double totalOrderedPallets) {
		this.totalOrderedPallets = totalOrderedPallets;
	}
	public Long getTotalReceivedPallets() {
		return totalReceivedPallets;
	}
	public void setTotalReceivedPallets(Long totalReceivedPallets) {
		this.totalReceivedPallets = totalReceivedPallets;
	}
	public Long getTotalOrderedNumberOfunits() {
		return totalOrderedNumberOfunits;
	}
	public void setTotalOrderedNumberOfunits(Long totalOrderedNumberOfunits) {
		this.totalOrderedNumberOfunits = totalOrderedNumberOfunits;
	}
	public Double getTotalReceivedNumberOfUnits() {
		return totalReceivedNumberOfUnits;
	}
	public void setTotalReceivedNumberOfUnits(Double totalReceivedNumberOfUnits) {
		this.totalReceivedNumberOfUnits = totalReceivedNumberOfUnits;
	}
	public OrderProductLocation() {
		
	}
    
    public Long getQueryProductId() {
		return queryProductId;
	}
	public void setQueryProductId(Long queryProductId) {
		this.queryProductId = queryProductId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getTotalDamagedQuantity() {
		return totalDamagedQuantity;
	}
	public void setTotalDamagedQuantity(Long totalDamagedQuantity) {
		this.totalDamagedQuantity = totalDamagedQuantity;
	}
	public Long getQuantitySold() {
		return quantitySold;
	}
	public void setQuantitySold(Long quantitySold) {
		this.quantitySold = quantitySold;
	}
	public Integer getReceivedPallets() {
		return receivedPallets;
	}
	public void setReceivedPallets(Integer receivedPallets) {
		this.receivedPallets = receivedPallets;
	}
	public Integer getReceivedNumberofUnits() {
		return receivedNumberofUnits;
	}
	public void setReceivedNumberofUnits(Integer receivedNumberofUnits) {
		this.receivedNumberofUnits = receivedNumberofUnits;
	}

	public Integer getDamageNumberOfUnits() {
		return damageNumberOfUnits;
	}

	public void setDamageNumberOfUnits(Integer damageNumberOfUnits) {
		this.damageNumberOfUnits = damageNumberOfUnits;
	}

	public Timestamp getExpiry() {
		return expiry;
	}
	public void setExpiry(Timestamp expiry) {
		this.expiry = expiry;
	}
	
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	public Float getReceivedQuantity() {
		return receivedQuantity;
	}
	public void setReceivedQuantity(Float receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}
	
	public Float getDamagedQuantity() {
		return damagedQuantity;
	}
	public void setDamagedQuantity(Float damagedQuantity) {
		this.damagedQuantity = damagedQuantity;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public OrderProduct getOrderProducts() {
		return orderProducts;
	}
	public void setOrderProducts(OrderProduct orderProducts) {
		this.orderProducts = orderProducts;
		this.productId = orderProducts.getProduct().getId();
	} 
}
