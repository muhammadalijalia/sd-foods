package org.caansoft.sdfood.prestashop.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.caansoft.sdfood.dto.StockCountHeaderDto;
import org.caansoft.sdfood.dto.StockCountLineItemDto;
import org.caansoft.sdfood.enums.StockCountStatus;
import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.model.StockCountHeader;
import org.caansoft.sdfood.model.StockCountLineItem;
import org.caansoft.sdfood.prestashop.service.PrestashopStockService;
import org.caansoft.sdfood.prestashopIntegration.PrestashopService;
import org.caansoft.sdfood.prestashopIntegration.StockAvailableDto;
import org.caansoft.sdfood.repo.ProductRepository;
import org.caansoft.sdfood.repo.StockCountLineItemRepository;
import org.caansoft.sdfood.service.StockCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PrestashopStockServiceImpl implements PrestashopStockService{

	@Override
	public HttpStatus resetProductStock(Long id, StockCountHeaderDto dto) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Autowired
//	ProductRepository productRepo;
//	
//	@Autowired
//	PrestashopService prestashopService;
//	
//	@Autowired
//	StockCountService stockCountService;
//	
//	@Autowired
//	StockCountLineItemRepository lineItemRepo;
//
//	@Override
//	public HttpStatus resetProductStock(Long id, StockCountHeaderDto stockCountDto) {
//		
//		StockCountHeader countHeader = new StockCountHeader();
//		countHeader.setId(id);
//		List<StockCountLineItem> lineItemList = lineItemRepo.findBystockCountHeader(countHeader);
//		
//		if (lineItemList != null && !lineItemList.isEmpty()) {				
//				
//				stockCountService.update(id, stockCountDto);
//
//				List<Long> ids = lineItemList.stream().map(o -> o.getProduct().getId()).collect(Collectors.toList());
//
//				if (ids != null && !ids.isEmpty()) {
//					try {
//						List<Product> products = productRepo.findByIdIn(ids);
//						if (products != null && !products.isEmpty()) {
//							Map<Integer, Product> prestaProductsIdMap = products.stream()
//									.collect(Collectors.toMap(Product::getPrestashopProductId, Function.identity()));
//							List<Integer> prestaIds = new ArrayList<>(prestaProductsIdMap.keySet());
//							String idParam = "";
//							for (Integer productId : prestaIds) {
//								idParam = idParam + productId + "|";
//							}
//							Map<Long, Product> productsIdMap = products.stream()
//									.collect(Collectors.toMap(Product::getId, Function.identity()));
//							HttpStatus status = prestashopService.resetStock(lineItemList, productsIdMap,
//									idParam);
//							if (!status.name().equals("OK")) {
//								stockCountDto.setStatus(StockCountStatus.IN_PROGRESS);
//								stockCountService.update(id, stockCountDto);
//							}
//							return status;
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}		
//		}		
//		return null;
//	}

}
