package org.caansoft.sdfood.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.enums.ProductStockTransactionType;

@Entity
@Table(name = "product_stock")
public class ProductStock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_on")
    private Timestamp createdOn;
    @Column(name = "updated_on")
    private Timestamp updatedOn;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "created_by")
    private String createdBy;
    @Enumerated(value = EnumType.STRING)
    protected Flag flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }
    
    @Column(name = "prestashop_product_id")
    private Long prestashopProductId;
	
    public Long getPrestashopProductId() {
		return prestashopProductId;
	}

	public void setPrestashopProductId(Long prestashopProductId) {
		this.prestashopProductId = prestashopProductId;
	}

	@ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "recorded_Date")
    private Timestamp recordedDate;

    @Column(name = "recorded_Time")
    private Timestamp recordedTime;

    @Column(name = "lotNumber")
    private String lotNumber;

    @Column(name = "expiry_Date")
    private Timestamp exprityDate;

    @Column(name = "quantity_in")
    private Integer quantityIn;

    @Column(name = "quantity_out")
    private Integer quantityOut;

    @Enumerated(value = EnumType.STRING)
    private ProductStockTransactionType transactionType;

    @Column(name = "balance")
    private Integer balance;

    @Column(name = "external_system_id")
    private Long externalSystemId;

    @Column(name = "external_system_name")
    private String externalSystemName;
    
    @Column(name = "input_stock_count")
    private Integer inputStockCount;
    
    @Column(name = "order_id")
    private Long orderId;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Transient
    private Long sumQuantityIn;

    @Transient
    private Long sumQuantityOut;      

	@Transient
    private String productName;

    public ProductStock(Product product, Long sumQuantityIn, Long sumQuantityOut) {
        this.product = product;
        this.sumQuantityIn = sumQuantityIn;
        this.sumQuantityOut = sumQuantityOut;
    }
    
    public ProductStock(Product product, String productName, Long sumQuantityIn, Long sumQuantityOut) {
        this.product = product;
        this.productName = productName;
        this.sumQuantityIn = sumQuantityIn;
        this.sumQuantityOut = sumQuantityOut;
    }

    public ProductStock() {

    }
    
    public ProductStock(Long id) {
    	this.id = id;
    }

    
    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

    public Integer getInputStockCount() {
		return inputStockCount;
	}

	public void setInputStockCount(Integer inputStockCount) {
		this.inputStockCount = inputStockCount;
	}

    public Long getSumQuantityIn() {
        return sumQuantityIn;
    }

    public void setSumQuantityIn(Long sumQuantityIn) {
        this.sumQuantityIn = sumQuantityIn;
    }

    public Long getSumQuantityOut() {
        return sumQuantityOut;
    }

    public void setSumQuantityOut(Long sumQuantityOut) {
        this.sumQuantityOut = sumQuantityOut;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product productId) {
        this.product = productId;
    }

    public Timestamp getRecordedTime() {
        return recordedTime;
    }

    public void setRecordedTime(Timestamp recordedTime) {
        this.recordedTime = recordedTime;
    }

    public Timestamp getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Timestamp recordedDate) {
        this.recordedDate = recordedDate;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public Timestamp getExprityDate() {
        return exprityDate;
    }

    public void setExprityDate(Timestamp exprityDate) {
        this.exprityDate = exprityDate;
    }

    public Integer getQuantityIn() {
        return quantityIn;
    }

    public void setQuantityIn(Integer quantityIn) {
        this.quantityIn = quantityIn;
    }

    public Integer getQuantityOut() {
        return quantityOut;
    }

    public void setQuantityOut(Integer quantityOut) {
        this.quantityOut = quantityOut;
    }

    public ProductStockTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(ProductStockTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Long getExternalSystemId() {
        return externalSystemId;
    }

    public void setExternalSystemId(Long externalSystemId) {
        this.externalSystemId = externalSystemId;
    }

    public String getExternalSystemName() {
        return externalSystemName;
    }

    public void setExternalSystemName(String externalSystemName) {
        this.externalSystemName = externalSystemName;
    }

}
