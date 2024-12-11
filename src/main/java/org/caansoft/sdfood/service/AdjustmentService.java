package org.caansoft.sdfood.service;

import java.util.List;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.dto.ProductStockBalanceDTO;
import org.caansoft.sdfood.dto.ProductStockQuantityDTO;
import org.caansoft.sdfood.request.AdjustmentRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdjustmentService {
	
	public Object getAdjustmentDetails(AdjustmentRequest request);

	ProductStockBalanceDTO productbalanceDetails(Long id, Long date);
	
	Page<ProductStockQuantityDTO> getProductStockCount(String name, Long startDate, Long endDate, Flag flag, Pageable pageable, Boolean isPageable);
	
}
