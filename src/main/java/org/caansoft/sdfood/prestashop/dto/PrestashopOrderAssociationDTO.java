package org.caansoft.sdfood.prestashop.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class PrestashopOrderAssociationDTO {

	private List<PrestashopOrderRowDTO> prestashopOrderRow;

	@XmlElementWrapper(name="order_rows")
	@XmlElement(name="order_row")
	public List<PrestashopOrderRowDTO> getPrestashopOrderRow() {
		return prestashopOrderRow;
	}

	public void setPrestashopOrderRow(List<PrestashopOrderRowDTO> prestashopOrderRow) {
		this.prestashopOrderRow = prestashopOrderRow;
	}
}
