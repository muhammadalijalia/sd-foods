package org.caansoft.sdfood.repo;

import org.caansoft.sdfood.model.Order;
import org.caansoft.sdfood.model.OrderPhoto;
import org.caansoft.core.model.Media;
import org.springframework.data.repository.CrudRepository;

public interface OrderPhotoRepository extends CrudRepository<OrderPhoto, Long> {

    public OrderPhoto findByOrderAndOrderMedia(Order order, Media media);
}
