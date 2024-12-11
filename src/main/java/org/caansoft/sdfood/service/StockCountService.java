package org.caansoft.sdfood.service;

import org.caansoft.core.dto.BaseDto;
import org.caansoft.core.service.BaseService;
import org.caansoft.sdfood.dto.StockCountHeaderDto;
import org.caansoft.sdfood.dto.StockCountLineItemDto;
import org.caansoft.sdfood.enums.StockCountStatus;
import org.caansoft.sdfood.model.StockCountHeader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public interface StockCountService extends BaseService {



    <T extends BaseDto> T initiateStockCount();
    <T extends BaseDto> T update(Long stockCountId, StockCountHeaderDto dto);
    <T extends BaseDto> T addLineItem(Long stockCountId, StockCountLineItemDto lineItem) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException;
    <T extends BaseDto> Page<T> find(StockCountHeader order, Date fromDate, Date toDate, StockCountStatus status, Pageable pageable);
}
