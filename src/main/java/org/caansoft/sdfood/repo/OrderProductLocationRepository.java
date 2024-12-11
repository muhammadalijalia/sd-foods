package org.caansoft.sdfood.repo;

import org.caansoft.sdfood.model.Order;
import org.caansoft.sdfood.model.OrderProduct;
import org.caansoft.sdfood.model.OrderProductLocation;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface OrderProductLocationRepository extends PagingAndSortingRepository<Order, Long>, JpaSpecificationExecutor<Order>  {

//	Specification<OrderProductLocation> findAllByExpiryLessThanEqualAndExpiryGreaterThanEqual(Date toDate,Date fromDate);
}
