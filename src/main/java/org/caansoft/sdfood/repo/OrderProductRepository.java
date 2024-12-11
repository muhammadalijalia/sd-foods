package org.caansoft.sdfood.repo;

import org.caansoft.sdfood.model.Order;
import org.caansoft.sdfood.model.OrderProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderProductRepository extends CrudRepository<OrderProduct,Long> {

    List<OrderProduct> findByOrder(Order order);
    
    OrderProduct findByOrderIdAndProductId(Long orderId, Long productId);
    
    List<OrderProduct> findAllByProductIdIn(List<Long> ids);
}
