package org.caansoft.sdfood.controller;

import org.caansoft.core.BaseRestMapper;
import org.caansoft.core.dto.response.Response;
import org.caansoft.sdfood.dto.AdjustmentDTO;
import org.caansoft.sdfood.dto.ProductLocationDto;
import org.caansoft.sdfood.dto.ProductStockBalanceDTO;
import org.caansoft.sdfood.dto.StockCountHeaderDto;
import org.caansoft.sdfood.dto.StockCountLineItemDto;
import org.caansoft.sdfood.dto.StockUpdateDTO;
import org.caansoft.sdfood.model.PrestashopCategory;
import org.caansoft.sdfood.model.PrestashopProduct;
import org.caansoft.sdfood.prestashop.dto.*;
import org.caansoft.sdfood.prestashop.model.PrestashopOrders;
import org.caansoft.sdfood.prestashop.service.CustomerService;
import org.caansoft.sdfood.prestashop.service.MessageService;
import org.caansoft.sdfood.prestashop.service.PrestashopAddressService;
import org.caansoft.sdfood.prestashop.service.PrestashopOrderDetailService;
import org.caansoft.sdfood.prestashop.service.PrestashopOrdersService;
import org.caansoft.sdfood.prestashop.service.PrestashopStockService;
import org.caansoft.sdfood.prestashop.service.TaxRuleGroupService;
import org.caansoft.sdfood.prestashopIntegration.ApiIntegration;
import org.caansoft.sdfood.prestashopIntegration.Prestashop;
import org.caansoft.sdfood.prestashopIntegration.PrestashopService;
import org.caansoft.sdfood.prestashopIntegration.StockAvailableDto;
import org.caansoft.sdfood.repo.PrestashopCategoryRepo;
import org.caansoft.sdfood.repo.PrestashopProductRepo;
import org.caansoft.sdfood.request.AdjustmentRequest;
import org.caansoft.sdfood.request.ProductExpiryReq;
import org.caansoft.sdfood.scheduler.ApiScheduler;
import org.caansoft.sdfood.service.AdjustmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PrestashopController implements BaseRestMapper {
	
	@Autowired
	private TaxRuleGroupService taxRuleGroup;

	@Autowired
	private PrestashopCategoryRepo categoryRepo;

	@Autowired
	private PrestashopProductRepo productRepo;

	@Autowired
	private PrestashopOrdersService<?> orderDetails;

	@Autowired
	private PrestashopOrderDetailService orderDetailsService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ApiScheduler apiScheduler;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private PrestashopAddressService addressService;
	
	@Autowired
	private AdjustmentService adjustmentService;
	
	@Autowired
	private PrestashopStockService stockService;

	@Autowired
	private PrestashopService prestashopService;


	@GetMapping("prestashop/categories")
	@ResponseBody
	public ResponseEntity<Response<List<PrestashopCategory>>> getPrestashopCategories() {
		List<PrestashopCategory> categories = (List<PrestashopCategory>) categoryRepo.findAll();
		return new ResponseEntity<Response<List<PrestashopCategory>>>(new Response<>(categories), HttpStatus.OK);
	}

	@GetMapping("prestashop/products")
	@ResponseBody
	public ResponseEntity<Response<List<PrestashopProduct>>> getPrestashopProducts() {
		List<PrestashopProduct> products = (List<PrestashopProduct>) productRepo.findAll();
		return new ResponseEntity<Response<List<PrestashopProduct>>>(new Response<>(products), HttpStatus.OK);
	}

//    @GetMapping("prestashop/orderDetails")
//    @ResponseBody
//    public ResponseEntity<List<PrestashopOrdersDTO>> getPrestashopOrderDetails(){
//    	List<PrestashopOrdersDTO> prestashopOrdersList = (List<PrestashopOrdersDTO>) orderDetails.getOrders();
//    	return new ResponseEntity<List<PrestashopOrdersDTO>>(prestashopOrdersList, HttpStatus.OK);
//    }

//    @GetMapping("prestashop/ordersList")
//    @ResponseBody
//    public ResponseEntity<Page<PrestashopOrdersDTO>> getPrestashopOrdersList(Pageable pageable){
//    	Page<PrestashopOrdersDTO> prestashopOrdersList = orderDetails.find(pageable);    	
//    	return new ResponseEntity<Page<PrestashopOrdersDTO>>(prestashopOrdersList, HttpStatus.OK);
//    }

//	@GetMapping("prestashop/ordersListentry")
//	@ResponseBody
//	public ResponseEntity<Page<PrestashopOrdersDTO>> getPrestashopOrdersListsecond(Pageable pageable) {
//		Page<PrestashopOrdersDTO> prestashopOrdersList = orderDetails.find(pageable);
////    	List<PrestashopOrdersDTO> list = new ArrayList<PrestashopOrdersDTO>();
////    	list=(List<PrestashopOrdersDTO>) prestashopOrdersList;    	
//		return new ResponseEntity<Page<PrestashopOrdersDTO>>(prestashopOrdersList, HttpStatus.OK);
//	}

//	@GetMapping("prestashop/ordersListByDate")
//	@ResponseBody
//	public ResponseEntity<List<PrestashopOrderSalesDTO>> findOrdersByDate(String startDate, String endDate,
//			Pageable pageable) {
//		startDate = "2022-04-07 00:00:00";
//		endDate = "2022-04-13 24:59:59";
//		List<PrestashopOrderSalesDTO> prestashopOrdersList = orderDetails.findOrdersByDate(startDate, endDate);
////    	List<PrestashopOrdersDTO> list = new ArrayList<PrestashopOrdersDTO>();
//////    	list=(List<PrestashopOrdersDTO>) prestashopOrdersList; 
////    	list = prestashopOrdersList.getContent();
//		return new ResponseEntity<List<PrestashopOrderSalesDTO>>(prestashopOrdersList, HttpStatus.OK);
//	}

	@GetMapping("prestashop/product/sales")
	@ResponseBody
	public ResponseEntity<Response<Page<PrestashopProductSalesDTO>>> findProductSales(
			@RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate,
			@RequestParam(required = false, defaultValue = "") String productName,
			@RequestParam(required = false, defaultValue = "true") Boolean isPageable, Pageable pageable) {
		Page<PrestashopProductSalesDTO> prestashopProductSalesDTOList = orderDetails.findProductSales(startDate,
				endDate, productName, pageable, isPageable);
		return new ResponseEntity<Response<Page<PrestashopProductSalesDTO>>>(
				new Response<>(prestashopProductSalesDTOList), HttpStatus.OK);
	}

	@GetMapping("prestashop/orders")
	@ResponseBody
	public ResponseEntity<Response<Page<PrestashopOrdersDTO>>> findOrders(@RequestParam(required = false) String id,
			@RequestParam(required = false) String referenceNo, @RequestParam(required = false) String productName,
			@RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate,
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String url,
			@RequestParam(required = false) Long empId,
			@RequestParam(required = false, defaultValue = "true") Boolean isPageable, Pageable pageable) {
		Page<PrestashopOrdersDTO> prestashopOrdersDTOList = orderDetails.findOrders(id, referenceNo, productName,
				startDate, endDate, status, url, empId, pageable, isPageable);
		return new ResponseEntity<Response<Page<PrestashopOrdersDTO>>>(new Response<>(prestashopOrdersDTOList),
				HttpStatus.OK);
	}

//    @GetMapping("prestashop/getDistinctProducts")
//    @ResponseBody
//    public ResponseEntity<Response<List<PrestashopOrderProductsDTO>>> findDistinctProducts(){
//    	
//    	List<PrestashopOrderProductsDTO> products = orderDetails.findDistinctProducts();
//    	return new ResponseEntity<Response<List<PrestashopOrderProductsDTO>>> (new Response<>(products), HttpStatus.OK);
//    }

	@GetMapping("prestashop/order/{id}/products")
	@ResponseBody
	public ResponseEntity<Response<Page<PrestashopOrderRowDTO>>> findOrderById(@PathVariable Integer id,
			Pageable pageable) {
		Page<PrestashopOrderRowDTO> prestashopOrdersDTOList = orderDetails.findOrderById(id, Pageable.unpaged());
		return new ResponseEntity<Response<Page<PrestashopOrderRowDTO>>>(new Response<>(prestashopOrdersDTOList),
				HttpStatus.OK);

	}

	@PutMapping("product/quantity/{id}")
	@ResponseBody
	ResponseEntity<Response<Object>> update(@RequestBody ProductExpiryReq request,
			@RequestParam(required = false) String action) {
		Object orderDto = orderDetailsService.update(request, action);
		return new ResponseEntity<Response<Object>>(new Response<>(orderDto), HttpStatus.OK);
	}

	@GetMapping("prestashop/product/expiry/{id}")
	@ResponseBody
	public ResponseEntity<Response<List<ProductLocationDto>>> findOrderById(@PathVariable Long id) {
		List<ProductLocationDto> opl = orderDetailsService.findProductExpiry(id);
		return new ResponseEntity<Response<List<ProductLocationDto>>>(new Response<>(opl), HttpStatus.OK);

	}
	
	@GetMapping("prestashop/product/expiries")
	@ResponseBody
	public ResponseEntity<Response<List<ExpiriesDTO>>> findProductExpiry(@RequestParam List<Long> ids) {
		List<ExpiriesDTO> opl = orderDetailsService.getProductExpiries(ids);
		return new ResponseEntity<Response<List<ExpiriesDTO>>>(new Response<>(opl), HttpStatus.OK);

	}

	@GetMapping("prestashop/order/{id}/processed-products")
	@ResponseBody
	public ResponseEntity<Response<ProductExpiryDetailDTO>> findExpiryDetails(@PathVariable Integer id) {
		ProductExpiryDetailDTO opl = orderDetailsService.findOrderDetails(id);
		return new ResponseEntity<Response<ProductExpiryDetailDTO>>(new Response<>(opl), HttpStatus.OK);

	}

	@GetMapping("prestashop/order/{id}")
	@ResponseBody
	public ResponseEntity<Response<PrestashopOrdersDTO>> findPrestashopOrder(@PathVariable Integer id) {
		PrestashopOrdersDTO opl = orderDetails.findPrestashopOrderById(id);
		return new ResponseEntity<Response<PrestashopOrdersDTO>>(new Response<>(opl), HttpStatus.OK);

	}

	@GetMapping("prestashop/customer/{id}")
	@ResponseBody
	public ResponseEntity<Response<CustomerDTO>> findCustomer(@PathVariable Integer id) {
		CustomerDTO customer = customerService.getCustomer(id);
		return new ResponseEntity<Response<CustomerDTO>>(new Response<>(customer), HttpStatus.OK);

	}

	@GetMapping("prestashop/customers")
	@ResponseBody
	public ResponseEntity<Response<List<CustomerDTO>>> findAllCustomer() {
		List<CustomerDTO> customer = customerService.getAllCustomers();
		return new ResponseEntity<Response<List<CustomerDTO>>>(new Response<>(customer), HttpStatus.OK);

	}

	@GetMapping("prestashop/order/{id}/update")
	@ResponseBody
	public ResponseEntity<Response<PrestashopOrdersDTO>> syncOrder(@PathVariable Integer id) {
		PrestashopOrdersDTO orders = apiScheduler.syncPrestashopOrder(id);
		return new ResponseEntity<Response<PrestashopOrdersDTO>>(new Response<PrestashopOrdersDTO>(orders),
				HttpStatus.OK);
	}

	@GetMapping("prestashop/address/{id}")
	@ResponseBody
	public ResponseEntity<Response<PrestashopAddressDTO>> getAddress(@PathVariable Integer id) {
		PrestashopAddressDTO addressDTO = addressService.getAddress(id);
		return new ResponseEntity<Response<PrestashopAddressDTO>>(new Response<PrestashopAddressDTO>(addressDTO),
				HttpStatus.OK);
	}
	
	@PutMapping("prestashop/adjustment")
	@ResponseBody
	public ResponseEntity<Response<Void>> getAdjustments(@RequestBody AdjustmentRequest request) {
		Object obj = adjustmentService.getAdjustmentDetails(request);
		return new ResponseEntity<Response<Void>>(new Response<>(null),
				HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("prestashop/messages")
	@ResponseBody
	public ResponseEntity<Response<List<MessageDTO>>> getAllMessages() {
		List<MessageDTO> messageDTOList = messageService.getAllMessages();
		return new ResponseEntity<Response<List<MessageDTO>>>(new Response<List<MessageDTO>>(messageDTOList),
				HttpStatus.OK);
	}
	
	@GetMapping("prestashop/messages/{id}")
	@ResponseBody
	public ResponseEntity<Response<List<MessageDTO>>> getMessage(@PathVariable Integer id) {
		List<MessageDTO> messageDTO = messageService.getMessage(id);
		return new ResponseEntity<Response<List<MessageDTO>>>(new Response<List<MessageDTO>>(messageDTO),
				HttpStatus.OK);
	}
	
	@GetMapping("prestashop/adjustment/product/{id}")
	@ResponseBody
	public ResponseEntity<Response<ProductStockBalanceDTO>> getProductBalance(
			@PathVariable Long id,
			@RequestParam(required = false) Long date) {
		ProductStockBalanceDTO balanceDTOList = adjustmentService.productbalanceDetails(id, date);
		return new ResponseEntity<Response<ProductStockBalanceDTO>>(new Response<ProductStockBalanceDTO>(balanceDTOList),
				HttpStatus.OK);
	}
	
	@GetMapping("prestashop/order/{id}/products/prepared")
	@ResponseBody
	public ResponseEntity<Response<Object>> getPreparedProducts(@PathVariable Integer id) {
	Object object = orderDetails.deliveryProducts(id);
	return new ResponseEntity<Response<Object>>(new Response<Object>(object), HttpStatus.OK);
	}
	
	@GetMapping("prestashop/tax-rule-groups")
	@ResponseBody
	public ResponseEntity<Response<List<TaxRuleGroupDto>>> getTaxRuleGroups() {
		List<TaxRuleGroupDto> dtoList = taxRuleGroup.getTaxRuleGroups();
		return new ResponseEntity<Response<List<TaxRuleGroupDto>>>(new Response<List<TaxRuleGroupDto>>(dtoList), HttpStatus.OK);
	}
	
	@PutMapping("prestashop/stocks/{id}")
	@ResponseBody
	public ResponseEntity<Response<Void>> resetStock(@PathVariable Long id, @RequestBody StockCountHeaderDto dto) {
		stockService.resetProductStock(id, dto);
		return new ResponseEntity<Response<Void>>(new Response<>(null), HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("prestashop/order/map-rider")
	@ResponseBody
	public ResponseEntity<Response<PrestashopOrders>> assignOrderToRider(
			@RequestParam(required = true) Integer orderId,
			@RequestParam(required = true) Long empId) {
		PrestashopOrders order = orderDetails.assignOrderToRider(orderId, empId);
		return new ResponseEntity<Response<PrestashopOrders>>(new Response<PrestashopOrders>(order), HttpStatus.OK);
	}
	
	@PutMapping("prestashop/product/stock")
	@ResponseBody
	public ResponseEntity<Response<StockAvailableDto>> updateProductStockCount (
			@RequestBody List<StockUpdateDTO> request) {
		StockAvailableDto stockDTO = prestashopService.updateProductStock(request);
		return new ResponseEntity<Response<StockAvailableDto>>(new Response<StockAvailableDto>(stockDTO), HttpStatus.OK);
	}
	
	@GetMapping("prestashop/order/statuses")
	@ResponseBody
	public ResponseEntity<Response<List<String>>> getDistinctOrderStatuses() {
		List<String> list = orderDetails.orderStatuses();
		return new ResponseEntity<Response<List<String>>>(new Response<List<String>>(list), HttpStatus.OK);
	}	
}
