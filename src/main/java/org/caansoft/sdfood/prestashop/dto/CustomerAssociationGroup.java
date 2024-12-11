package org.caansoft.sdfood.prestashop.dto;

import javax.xml.bind.annotation.XmlElement;

public class CustomerAssociationGroup {

	private Integer id;

	@XmlElement(name="id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
