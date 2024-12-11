package org.caansoft.sdfood.request;

import org.caansoft.sdfood.dto.AdjustmentDTO;

public class AdjustmentRequest {

	private AdjustmentDTO initialValues;

	public AdjustmentDTO getInitialValues() {
		return initialValues;
	}

	public void setInitialValues(AdjustmentDTO initialValues) {
		this.initialValues = initialValues;
	}
}
