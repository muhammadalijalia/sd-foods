package org.caansoft.sdfood.prestashop.dto;

import javax.xml.bind.annotation.XmlElement;

public class PrestashopAddressDTO {

	private Integer id;
	private String idCustomer;
	private String idManufacturer;
	private String idSupplier;
	private String idWareshouse;
	private String idCountry;
	private String idState;
	private String alias;
	private String company;
	private String lastName;
	private String firstName;
	private String vatNumber;
	private String address1;
	private String address2;
	private String postCode;
	private String city;
	private String other;
	private String phone;
	private String phoneMobile;	
	private String dni;
	private String deleted;
	private String dateAdd;
	private String dateUpd;
	
	@XmlElement(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement(name="id_customer")
	public String getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	@XmlElement(name="id_manufacturer")
	public String getIdManufacturer() {
		return idManufacturer;
	}
	public void setIdManufacturer(String idManufacturer) {
		this.idManufacturer = idManufacturer;
	}
	
	@XmlElement(name="id_supplier")
	public String getIdSupplier() {
		return idSupplier;
	}
	public void setIdSupplier(String idSupplier) {
		this.idSupplier = idSupplier;
	}
	
	@XmlElement(name="id_warehouse")
	public String getIdWareshouse() {
		return idWareshouse;
	}
	public void setIdWareshouse(String idWareshouse) {
		this.idWareshouse = idWareshouse;
	}
	
	@XmlElement(name="id_country")
	public String getIdCountry() {
		return idCountry;
	}
	public void setIdCountry(String idCountry) {
		this.idCountry = idCountry;
	}
	
	@XmlElement(name="id_state")
	public String getIdState() {
		return idState;
	}
	public void setIdState(String idState) {
		this.idState = idState;
	}
	
	@XmlElement(name="alias")
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@XmlElement(name="company")
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	@XmlElement(name="lastname")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@XmlElement(name="firstname")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@XmlElement(name="vat_number")
	public String getVatNumber() {
		return vatNumber;
	}
	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}
	
	@XmlElement(name="address1")
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	@XmlElement(name="address2")
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	@XmlElement(name="postcode")
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	@XmlElement(name="city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@XmlElement(name="other")
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	
	@XmlElement(name="phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@XmlElement(name="phone_mobile")
	public String getPhoneMobile() {
		return phoneMobile;
	}
	public void setPhoneMobile(String phoneMobile) {
		this.phoneMobile = phoneMobile;
	}
	
	@XmlElement(name="dni")
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	@XmlElement(name="deleted")
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	@XmlElement(name="date_add")
	public String getDateAdd() {
		return dateAdd;
	}
	public void setDateAdd(String dateAdd) {
		this.dateAdd = dateAdd;
	}
	
	@XmlElement(name="date_upd")
	public String getDateUpd() {
		return dateUpd;
	}
	public void setDateUpd(String dateUpd) {
		this.dateUpd = dateUpd;
	}
}
