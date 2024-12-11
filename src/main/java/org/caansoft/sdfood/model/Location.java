package org.caansoft.sdfood.model;

import org.caansoft.core.model.BaseEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "warehouse_locations")
public class Location extends BaseEntity {

    private String name;
    
//    @OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private List<Product> products;
    
    @ManyToOne
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;
    
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<OrderProductLocation> orderProductsLocation = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OrderProductLocation> getOrderProductsLocation() {
		return orderProductsLocation;
	}

	public void setOrderProductsLocation(List<OrderProductLocation> orderProductsLocation) {
		this.orderProductsLocation = orderProductsLocation;
	}

//	public List<Product> getProducts() {
//		return products;
//	}
//
//	public void setProducts(List<Product> product) {
//		this.products = product;
//	}
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}


	@Override
	public boolean equals(Object o) {
		// self check
		if (this == o)
			return true;
		// null check
		if (o == null)
			return false;
		// type check and cast
		if (getClass() != o.getClass())
			return false;
		Warehouse w = (Warehouse) o;
		// field comparison
		return id.equals(w.getId());
	}


	@Override
	public int hashCode() {
		return id.hashCode();
	}
    
    
}
