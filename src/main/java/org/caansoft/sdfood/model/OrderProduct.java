package org.caansoft.sdfood.model;

import org.caansoft.core.model.BaseEntity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "order_products")
public class OrderProduct extends BaseEntity {	

    public ProductName getProductName() {
		return productName;
	}

	public void setProductName(ProductName productName) {
		this.productName = productName;
	}

	@ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;
    
    private Float quantity;
    @Column(name = "number_of_units")
    private Integer numberOfUnits;
    
    @OneToMany(mappedBy = "orderProducts", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<OrderProductLocation> orderProductsLocations;

    @OneToMany(mappedBy = "orderProduct", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<OrderProductPhoto> orderProductPhotos;
    
    @ManyToOne
    @JoinColumn(name = "product_name_id")
    private ProductName productName;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public Float getQuantity() {
		return quantity;
	}

	public void setQuantity(Float quantity) {
		this.quantity = quantity;
	}

    public Integer getNumberOfUnits() {
        return numberOfUnits;
    }

    public void setNumberOfUnits(Integer numberOfUnits) {
        this.numberOfUnits = numberOfUnits;
    }

    public List<OrderProductLocation> getOrderProductsLocations() {
		return orderProductsLocations;
	}

	public void setOrderProductsLocations(List<OrderProductLocation> orderProductsLocations) {
        if(this.orderProductsLocations != null){
            this.orderProductsLocations.clear();
            this.orderProductsLocations.addAll(orderProductsLocations);
        }else{
            this.orderProductsLocations = orderProductsLocations;
        }
	}

    public List<OrderProductPhoto> getOrderProductPhotos() {
        return orderProductPhotos;
    }

    public void setOrderProductPhotos(List<OrderProductPhoto> orderProductPhotos) {
        if(this.orderProductPhotos != null){
            this.orderProductPhotos.clear();
            this.orderProductPhotos.addAll(orderProductPhotos);
        }else{
            this.orderProductPhotos = orderProductPhotos;
        }
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
