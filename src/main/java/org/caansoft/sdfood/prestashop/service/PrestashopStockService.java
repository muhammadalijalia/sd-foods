package org.caansoft.sdfood.prestashop.service;

import java.util.List;

import org.caansoft.sdfood.dto.StockCountHeaderDto;
import org.springframework.http.HttpStatus;

public interface PrestashopStockService {

	public HttpStatus resetProductStock (Long id, StockCountHeaderDto dto);
}
