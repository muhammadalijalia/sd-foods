package org.caansoft.sdfood.prestashop.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class CustomerAssociationDTO {

	private List<CustomerAssociationGroup> group;

	@XmlElementWrapper(name="groups")
	@XmlElement(name="group")
	public List<CustomerAssociationGroup> getGroup() {
		return group;
	}

	public void setGroup(List<CustomerAssociationGroup> group) {
		this.group = group;
	}
	
}
