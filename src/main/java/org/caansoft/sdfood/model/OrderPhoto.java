package org.caansoft.sdfood.model;

import org.caansoft.core.model.BaseEntity;
import org.caansoft.core.model.Media;

import javax.persistence.*;

@Entity
@Table(name="order_photos")
public class OrderPhoto extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_media_id")
    private Media orderMedia;

    private Boolean measurement;
    private Boolean delivery;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Media getOrderMedia() {
        return orderMedia;
    }

    public void setOrderMedia(Media orderMedia) {
        this.orderMedia = orderMedia;
    }

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
