package org.caansoft.sdfood.prestashop.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

import javax.persistence.OneToMany;

@Entity
@Transactional
@Table(name="prestashop_customers")
public class PrestashopCustomer {

	@Id
	@Column(name="id")
	private Integer id;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(name="date_add")
	private Timestamp dateAdd;
	
	@Column(name="date_upd")
	private Timestamp dateUpd;
	
	@Column(name="active_flag")
	private Integer activeFlag;
	
	@Column(name="group_id")
	private String groupId;
	
	@Column(name="siret")
	private String siret;
	
	@Column(name="company")
	private String company;
	
	@Column(name="email")
	private String email;
	
	@Column(name="website")
	private String website;
	
	@OneToMany(mappedBy = "customer")
	private List<PrestashopOrders> order;

	public List<PrestashopOrders> getOrder() {
		return order;
	}

	public void setOrder(List<PrestashopOrders> order) {
		this.order = order;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getDateAdd() {
		return dateAdd;
	}

	public void setDateAdd(Timestamp dateAdd) {
		this.dateAdd = dateAdd;
	}

	public Timestamp getDateUpd() {
		return dateUpd;
	}

	public void setDateUpd(Timestamp dateUpd) {
		this.dateUpd = dateUpd;
	}

	public Integer getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Integer activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
