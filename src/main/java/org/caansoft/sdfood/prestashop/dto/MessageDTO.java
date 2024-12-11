package org.caansoft.sdfood.prestashop.dto;

import javax.xml.bind.annotation.XmlElement;

public class MessageDTO {

	private Integer id;
	private Integer idCart;
	private Integer idOrder;
	private Integer idCustomer;
	private Integer idEmployee;
	private String message;
	private Integer privateId;
	private String dateAdd;
	
	@XmlElement(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement(name="id_cart")
	public Integer getIdCart() {
		return idCart;
	}
	public void setIdCart(Integer idCart) {
		this.idCart = idCart;
	}
	
	@XmlElement(name="id_order")
	public Integer getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}
	
	@XmlElement(name="id_customer")
	public Integer getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(Integer idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	@XmlElement(name="id_employee")
	public Integer getIdEmployee() {
		return idEmployee;
	}
	public void setIdEmployee(Integer idEmployee) {
		this.idEmployee = idEmployee;
	}
	
	@XmlElement(name="message")
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@XmlElement(name="private")
	public Integer getPrivateId() {
		return privateId;
	}
	public void setPrivateId(Integer privateId) {
		this.privateId = privateId;
	}
	
	@XmlElement(name="date_add")
	public String getDateAdd() {
		return dateAdd;
	}
	public void setDateAdd(String dateAdd) {
		this.dateAdd = dateAdd;
	}
	
}
