package org.caansoft.sdfood.prestashop.dto;

import javax.xml.bind.annotation.XmlElement;

public class CustomerDTO {

	private Integer id;
	private String idDefaultGroup;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String company;
	private String siret;
	private String website;
	private Integer active;
	private String dateAdd;
	private String dateUpd;
	private CustomerAssociationDTO association;
	
	@XmlElement(name="associations")
	public CustomerAssociationDTO getAssociation() {
		return association;
	}
	public void setAssociation(CustomerAssociationDTO association) {
		this.association = association;
	}
	
	@XmlElement(name="id_default_group")
	public String getIdDefaultGroup() {
		return idDefaultGroup;
	}
	public void setIdDefaultGroup(String idDefaultGroup) {
		this.idDefaultGroup = idDefaultGroup;
	}
	
	@XmlElement(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement(name="passwd")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@XmlElement(name="firstname")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@XmlElement(name="lastname")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@XmlElement(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@XmlElement(name="company")
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	@XmlElement(name="siret")
	public String getSiret() {
		return siret;
	}
	public void setSiret(String siret) {
		this.siret = siret;
	}
	
	@XmlElement(name="active")
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
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
	
	@XmlElement(name="website")
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
}
