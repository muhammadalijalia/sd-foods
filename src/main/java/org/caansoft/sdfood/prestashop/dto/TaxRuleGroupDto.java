package org.caansoft.sdfood.prestashop.dto;

import javax.xml.bind.annotation.XmlElement;

public class TaxRuleGroupDto {

	private Integer id;
	private String name;
	private Boolean active;
	private Boolean deleted;
	private String dateAdd;
	private String dateUpd;
	
	@XmlElement(name="id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name="active")
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@XmlElement(name="deleted")
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
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
