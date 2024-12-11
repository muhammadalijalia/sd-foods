package org.caansoft.sdfood.service;

import org.caansoft.sdfood.dto.InventoryOrderedDto;
import org.caansoft.sdfood.dto.StatsDto;
import org.caansoft.sdfood.enums.OrderStatus;
import org.caansoft.sdfood.model.Order;
import org.caansoft.sdfood.model.OrderProductLocation;
import org.caansoft.core.dto.BaseDto;
import org.caansoft.core.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderService extends BaseService {

    public <T extends BaseDto> Page<T> find(Order order, Date fromDate, Date toDate, OrderStatus status, boolean project, String productName,Date startDeliveryDate, Date endDeliveryDate, Pageable pageable);

    StatsDto dashboard(Order con, Date fromDate, Date toDate, Pageable pageable);
    
    public Page<InventoryOrderedDto> getInventoryOrdered(Long startDate, Long endDate, String productName, String vendorName, 
    		Boolean isPageable, Pageable pageable);
}
