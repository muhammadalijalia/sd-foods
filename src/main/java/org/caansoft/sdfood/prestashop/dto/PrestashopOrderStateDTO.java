package org.caansoft.sdfood.prestashop.dto;

import java.util.List;

import org.caansoft.sdfood.prestashopIntegration.PrestashopProductName;

public class PrestashopOrderStateDTO {

	private Integer id;
	
	private List<PrestashopProductName> name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<PrestashopProductName> getName() {
		return name;
	}

	public void setName(List<PrestashopProductName> name) {
		this.name = name;
	}
}
