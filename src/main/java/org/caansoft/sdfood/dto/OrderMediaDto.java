package org.caansoft.sdfood.dto;

import org.caansoft.core.dto.MediaDto;

public class OrderMediaDto extends MediaDto {

    private Boolean measurement;
    private Boolean delivery;

    public Boolean getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Boolean measurement) {
        this.measurement = measurement;
    }

    public Boolean getDelivery() {
        return delivery;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }
}
