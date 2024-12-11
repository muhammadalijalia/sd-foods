package org.caansoft.sdfood.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.caansoft.core.model.BaseEntity;

@Entity
@Table(name="product_names")
public class ProductName extends BaseEntity{

	@Column(name = "name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@OneToMany(mappedBy = "productName", fetch = FetchType.EAGER)
	private List<OrderProduct> orderProducts;
	
	public List<OrderProduct> getOrderProduct() {
		return orderProducts;
	}

	public void setOrderProduct(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
