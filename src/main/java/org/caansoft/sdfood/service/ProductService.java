package org.caansoft.sdfood.service;


import java.util.Date;

import org.caansoft.core.dto.BaseDto;
import org.caansoft.core.service.BaseService;
import org.caansoft.sdfood.enums.OrderStatus;
import org.caansoft.sdfood.model.Order;
import org.caansoft.sdfood.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends BaseService {
	public <T extends BaseDto> Page<T> find(Product product, Date fromDate, Date toDate, Pageable pageable);
}

