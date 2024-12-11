package org.caansoft.sdfood.prestashop.service;

import java.util.List;

import org.caansoft.sdfood.dto.ProductLocationDto;
import org.caansoft.sdfood.prestashop.dto.ExpiriesDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderDetailsDTO;
import org.caansoft.sdfood.prestashop.dto.ProductExpiryDetailDTO;

public interface PrestashopOrderDetailService {

	PrestashopOrderDetailsDTO getOrderDetails(Integer id);
	
	public Object update(ProductExpiryDetailDTO expiry, String orderState);
	
	public List<ProductLocationDto> findProductExpiry(Long id);
	
	public ProductExpiryDetailDTO findOrderDetails(Integer id);
	
	public List<ExpiriesDTO> getProductExpiries(List<Long> ids);
}
