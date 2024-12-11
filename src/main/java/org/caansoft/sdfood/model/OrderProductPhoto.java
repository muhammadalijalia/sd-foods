package org.caansoft.sdfood.model;

import org.caansoft.core.model.BaseEntity;
import org.caansoft.core.model.Media;

import javax.persistence.*;

@Entity
@Table(name="order_product_photos")
public class OrderProductPhoto extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "media_id")
    private Media media;

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
