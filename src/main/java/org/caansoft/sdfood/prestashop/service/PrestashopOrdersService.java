package org.caansoft.sdfood.prestashop.service;

import java.util.Date;
import java.util.List;

import org.caansoft.sdfood.dto.StatsDto;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderProductsDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderRowDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderSalesDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrdersDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopProductSalesDTO;
import org.caansoft.sdfood.prestashop.dto.ProductExpiryDetailDTO;
import org.caansoft.sdfood.prestashop.model.PrestashopOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PrestashopOrdersService<T> {

//	public Object getOrders();

	Page<PrestashopOrdersDTO> find(Pageable pageable);

	List<PrestashopOrderSalesDTO> findOrdersByDate(String startDate, String endDate);

	Page<PrestashopProductSalesDTO> findProductSales(Long startDate, Long endDate, String productName, 
			Pageable pageable, Boolean isPageable);
	
	StatsDto findTotalOrdersAndAmount(Date startDate, Date endDate);
	
	Page<PrestashopOrdersDTO> findOrders(String id, String referenceNo, String productName, Long startDate, Long endDate, String status, String url, Long empId, Pageable pageable, Boolean isPageable);
	
	List<PrestashopOrderProductsDTO> findDistinctProducts();
	
	Page<PrestashopOrderRowDTO> findOrderById(Integer id, Pageable pageable);
	
	PrestashopOrdersDTO findPrestashopOrderById(Integer id);
	
	ProductExpiryDetailDTO deliveryProducts(Integer id);
	
	PrestashopOrders assignOrderToRider(Integer orderId, Long empId);
	
	List<String> orderStatuses();
	
//	List<String> getPrestashopOrderStatuses();
}
