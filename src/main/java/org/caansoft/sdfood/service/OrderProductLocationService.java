package org.caansoft.sdfood.service;

import org.caansoft.sdfood.enums.OrderStatus;
import org.caansoft.sdfood.model.Order;
import org.caansoft.sdfood.model.OrderProductLocation;
import org.caansoft.core.dto.BaseDto;
import org.caansoft.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public interface OrderProductLocationService extends BaseService {
	
//	Specification<OrderProductLocation> findAllByExpiryLessThanEqualAndExpiryGreaterThanEqual(Date toDate,Date fromDate);
}
