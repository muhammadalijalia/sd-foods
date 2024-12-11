package org.caansoft.sdfood.scheduler;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.caansoft.sdfood.enums.ProductStockTransactionType;
import org.caansoft.sdfood.enums.ScheduledJobUpdateStatus;
import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.model.ProductStock;
import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.prestashop.dto.CustomerDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderDetailsDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderRowDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrdersDTO;
import org.caansoft.sdfood.prestashop.model.PrestashopCustomer;
import org.caansoft.sdfood.prestashop.model.PrestashopOrderProducts;
import org.caansoft.sdfood.prestashop.model.PrestashopOrders;
import org.caansoft.sdfood.prestashop.service.CustomerService;
import org.caansoft.sdfood.prestashop.service.PrestashopOrderStateService;
import org.caansoft.sdfood.prestashop.serviceImpl.PrestashopOrderDetailsServiceImpl;
import org.caansoft.sdfood.prestashopIntegration.ApiIntegration;
import org.caansoft.sdfood.prestashopIntegration.Prestashop;
import org.caansoft.sdfood.prestashopIntegration.PrestashopProductDto;
import org.caansoft.sdfood.repo.PrestashopCustomerRepository;
import org.caansoft.sdfood.repo.PrestashopOrdersRepository;
import org.caansoft.sdfood.repo.ProductRepository;
import org.caansoft.sdfood.repo.ProductStockRepository;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiScheduler {

	private static final Logger logger = LoggerFactory.getLogger(ApiScheduler.class);

	@Autowired
	private PrestashopOrdersRepository prestashopOrderRepo;
	
	@Autowired
	private ProductStockRepository productStockRepo;

	@Autowired
	private PrestashopCustomerRepository customerRepo;

	@Autowired
	public PrestashopOrderStateService orderStateImpl;

	@Autowired
	public CustomerService customerService;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ApiIntegration api;

	@Value("${prestashop.host}")
	private String prestashopHost;

	@Value("${prestashop.api.token}")
	private String prestashopApiToken;

	@Value("${api.records}")
	private String noOfRecords;
	
	@Value("${update.job.time}")
	private Integer time;

	@Value("${old.orders.update.job:false}")
	private String oldOrderJob;

	@Autowired
	public PrestashopOrderStateService impl;

	@Autowired
	private PrestashopOrderDetailsServiceImpl prestashopOrderDetailServiceImpl;

	public String url = new String();

	@Scheduled(cron = "${api.interval}")
	public void insertPrestashopOrders() {

		try {
			Integer i = prestashopOrderRepo.findLastRecordId();
			String api = "insert";
			if (i == null)
				i = 0;
			Prestashop prestashop = getOrders(i, api);
			savePrestashopOrders(prestashop);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "${updateApi.interval}")
	public void updatePrestashopOrders() {	
		
		/* First process old unprocessed records if any */
			updateOldUnProcessedRecords();
			int i = 0;
			System.out.println("regular update api");
			try {
				String api = "update";
				Prestashop prestashop = getOrders(i, api);
				if (prestashop.getPrestashopOrdersList() != null && !prestashop.getPrestashopOrdersList().isEmpty()) {
					update(prestashop.getPrestashopOrdersList());					
				}
			} catch (Exception e) {
				logger.error("could not update the prestashop orders in database");
				throw new RuntimeException("Exception occured while updating the prestashop orders on sdfoods system " + e);
			}		
	}
	
	private void saveStockForProductSold(PrestashopOrders order) throws Exception {

		try {
			for (PrestashopOrderProducts por : order.getOrderProducts()) {
				ProductStock ps = new ProductStock();
				try {
					List<Product> products = productRepo.findByPrestashopProductIdIn(Arrays.asList(por.getProductId()));
					if (products != null && !products.isEmpty()) {
						for (Product p : products) {
							ps.setProduct(p);
						}
					} else {
						logger.info("Prestashop Product with id: " + por.getProductId()
								+ " is not mapped with any product");
					}
				} catch (Exception e) {
					logger.error("Could not fetch the product for saving in product stock sold.");
					throw new RuntimeException("Error occured while fetching the product id for stock sold " + e);
				}
				ps.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				ps.setQuantityOut(por.getProductQuantity() != null ? por.getProductQuantity() : null);
				ps.setTransactionType(ProductStockTransactionType.SOLD);
				ps.setUpdatedOn(order.getDateOrderUpdated() != null ? order.getDateOrderUpdated() : null);
				ps.setPrestashopProductId(por.getProductId() != null ? por.getProductId().longValue() : null);
				ps.setOrderId(order.getPrestashopOrderId() != null ? order.getPrestashopOrderId().longValue() : null);
				productStockRepo.save(ps);
			}
		} catch (Exception e) {
			logger.error("Could not save the product stock sold for order id: " + order.getPrestashopOrderId());
			throw new RuntimeException("Error occured while fetching the data for prestashop orders " + e);
		}
	}
		
	public void updateOldUnProcessedRecords() {
		try {
			Prestashop prestashop = getOldUnProcessedOrders();
			if (prestashop != null && prestashop.getPrestashopOrdersList() != null && !prestashop.getPrestashopOrdersList().isEmpty()) {
				update(prestashop.getPrestashopOrdersList());
			} else {
				logger.info("No orders to process");
			}
		} catch (Exception e) {
			logger.error("Error occured while updating the old orders");
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public PrestashopOrdersDTO syncPrestashopOrder(Integer id) {
		int i = id;
		try {
			String api = "sync";
			Prestashop prestashop = getOrders(i, api);
			if (prestashop.getPrestashopOrderDetails() != null) {
				List<PrestashopOrdersDTO> orders = new ArrayList<>();
				orders.add(prestashop.getPrestashopOrderDetails());
				update(orders);
				return prestashop.getPrestashopOrderDetails();
			}
		} catch (Exception e) {
			logger.error("Order with id: " + id + " could not be updated due to some error");
			throw new RuntimeException(e);
		}
		return null;
	}

	@Scheduled(cron = "${oldRecords}")
	public void updateOldRecords() {
		if (oldOrderJob.equals("true")) {
			int i;
			try {
				System.out.println("Job started" + LocalDateTime.now());
				String api = "oldRecordsUpdate";
				for (i = 1; i != 0;) {
					System.out.println("Loop started" + LocalDateTime.now());
					Prestashop prestashop = getOrders(i, api);
					if (!prestashop.getPrestashopOrdersList().isEmpty()) {
						List<PrestashopOrdersDTO> orderDTO = new ArrayList<PrestashopOrdersDTO>();
						for (PrestashopOrdersDTO por : prestashop.getPrestashopOrdersList()) {
							if (!por.getDateAdd().equals(por.getDateUpd())) {
								orderDTO.add(por);
							}
						}
						prestashop.setPrestashopOrdersList(orderDTO);
						update(prestashop.getPrestashopOrdersList());
						i = i + 99;
						System.out.println("Loop ended" + LocalDateTime.now());
					} else {
						i = 0;
					}
				}
				System.out.println("Job ended --- " + LocalDateTime.now());
			} catch (Exception e) {

			}
		}
	}	

	@Transactional
	public void update(List<PrestashopOrdersDTO> ordersDTOList) {
		
		try {
			Map<Integer, String> map = impl.getOrderStates();
			List<Integer> idList = new ArrayList<>();
			for (PrestashopOrdersDTO dto : ordersDTOList) {
				idList.add(dto.getId());
			}
			List<PrestashopOrders> orders = prestashopOrderRepo.findAllByPrestashopOrderIdIn(idList);
			Map<Integer, PrestashopOrders> orderIdMap = new HashMap<>();
			for (PrestashopOrders po : orders) {
				orderIdMap.put(po.getPrestashopOrderId(), po);
			}
			for (PrestashopOrdersDTO orderDTO : ordersDTOList) {

				PrestashopOrders order = orderIdMap.get(orderDTO.getId());
				
				try {					

					if (order != null && !order.getOrderCurrentState().equals(31) && !order.getOrderCurrentState().equals(32)) {
						order.setDateOrderUpdated(Timestamp.valueOf(orderDTO.getDateUpd()));
						order.setOrderCurrentState(Integer.valueOf(orderDTO.getCurrentState()));
						order.setProcessed(map.get(Integer.valueOf(orderDTO.getCurrentState())));

						Map<Integer, PrestashopOrderProducts> localProductsMap = new HashMap<Integer, PrestashopOrderProducts>();
						Map<Integer, PrestashopOrderProducts> prestashopProductsMap = new HashMap<Integer, PrestashopOrderProducts>();						

						for (PrestashopOrderProducts product : order.getOrderProducts()) {
							localProductsMap.put(product.getProductId(), product);
						}

						List<Integer> orderDetailIds = orderDTO.getPrestashopOrderAssociationDTO()
								.getPrestashopOrderRow().stream().map(row -> row.getId()).collect(Collectors.toList());
						if (orderDetailIds != null && !orderDetailIds.isEmpty()) {
							List<PrestashopOrderDetailsDTO> orderDetailList = prestashopOrderDetailServiceImpl
									.getOrderDetailList(orderDetailIds).getPrestashopOrderDetailList();
							Map<Integer, PrestashopOrderDetailsDTO> pMap = new HashMap<>();
							orderDetailList.forEach(orderDetail -> pMap.put(orderDetail.getId(), orderDetail));
												

						for (PrestashopOrderRowDTO por : orderDTO.getPrestashopOrderAssociationDTO()
								.getPrestashopOrderRow()) {

							PrestashopOrderProducts prestashopOrderProducts = new PrestashopOrderProducts();							
							prestashopOrderProducts.setProductId(por.getProductId());
							prestashopOrderProducts.setProductName(por.getProductName());
							prestashopOrderProducts.setProductDetailId(por.getId());
							prestashopOrderProducts.setOrder(order);
							prestashopOrderProducts.setProductQuantity(por.getProductQuantity());
							prestashopOrderProducts.setProducdPriceTaxIncl(por.getUnitPriceTaxIncl());
							prestashopOrderProducts.setProductPrice(por.getProductPrice());
							prestashopOrderProducts
									.setProductCostPrice(pMap.get(por.getId()).getOriginalWholesalePrice());
							prestashopProductsMap.put(por.getProductId(), prestashopOrderProducts);
						}
						for (Map.Entry<Integer, PrestashopOrderProducts> entry : localProductsMap.entrySet()) {

							if (prestashopProductsMap.containsKey(entry.getKey())) {

								PrestashopOrderProducts product = prestashopProductsMap.get(entry.getKey());
								product.setId(entry.getValue().getId());
								prestashopProductsMap.put(entry.getKey(), product);

							} 							
						}
						}
						ArrayList<PrestashopOrderProducts> updatedProductList = new ArrayList<>(prestashopProductsMap.values());
						order.getOrderProducts().clear();
						order.getOrderProducts().addAll(updatedProductList);
						order.setJobUpdateStatus(ScheduledJobUpdateStatus.SUCCESSFULL);
						order = prestashopOrderRepo.save(order);
						if (order.getJobUpdateStatus().equals(ScheduledJobUpdateStatus.SUCCESSFULL) &&
								(order.getOrderCurrentState().equals(31) || order.getOrderCurrentState().equals(32))) {
							saveStockForProductSold(order);
						}						
					}

				} catch (Exception e) {
					logger.error("Order with id: " + order.getPrestashopOrderId() + " could not be updated successfully");
					order = orderIdMap.get(orderDTO.getId());
					order.setJobUpdateStatus(ScheduledJobUpdateStatus.FAILED);
					prestashopOrderRepo.save(order);
					throw new RuntimeException("Exception occured while updating the order: " + order.getPrestashopOrderId());
				}
			}
		} catch (Exception e) {
			logger.info("Orders could not be updated");
			throw new RuntimeException(e);
		}
	}

	@Transactional
	private void delete(List<PrestashopOrdersDTO> orderList) {
		for (PrestashopOrdersDTO dto : orderList) {
			prestashopOrderRepo.deleteByPrestashopOrderId(dto.getId());
		}
	}
	
	@Transactional
	public Prestashop getOldUnProcessedOrders() throws Exception {
		Prestashop prestashop = null;
		try {
			List<PrestashopOrders> orders = prestashopOrderRepo.findAllByProcessedIsNull();
			if (orders != null && !orders.isEmpty()) {
				String filter = "filter[id]=[";
				for (PrestashopOrders por : orders) {
					filter = filter + por.getPrestashopOrderId() + "|";
				}
				filter = filter + "]";
				url = prestashopHost + "/api/orders?" + filter + "&output_format=XML&display=full";
				RestTemplate restTemplate = new RestTemplate();
				restTemplate.getMessageConverters().add(0, new Jaxb2RootElementHttpMessageConverter());
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setBasicAuth(prestashopApiToken, "");
				HttpEntity request = new HttpEntity(httpHeaders);
				try {
					ResponseEntity<Prestashop> response = restTemplate.exchange(url, HttpMethod.GET, request,
							Prestashop.class);

					if (response.getStatusCode() == HttpStatus.OK) {
						if (response.getBody() != null) {
							logger.info("Order details body" + response.getBody());
							prestashop = response.getBody();
							return prestashop;
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					throw new Exception("There might be a network failure while fetching orders. Exception is: " + ex);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Exception while executing query: " + e);
		}
		return prestashop;
	}

	@Transactional
	public Prestashop getOrders(Integer i, String api) {

		Prestashop prestashop = null;
		try {
			if (api == "insert") {
				url = prestashopHost + "/api/orders/?limit=" + i + "," + noOfRecords
						+ "&output_format=XML&display=full";

			}
			if (api == "update") {
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.HOUR, time);
				Date date = cal.getTime();
				String pattern = "yyyy-MM-dd hh:mm:ss";
				SimpleDateFormat simple = new SimpleDateFormat(pattern);
				url = prestashopHost + "/api/orders?output_format=XML&display=full&filter[date_upd]=>["
						+ simple.format(date) + "]&date=1";
				System.out.println("Time is: " + url);
			}
			if (api == "oldRecordsUpdate") {
				Calendar cal = Calendar.getInstance();
				cal.set(2023, 01, 31, 0, 0);
				Date date = cal.getTime();
				String pattern = "yyyy-MM-dd hh:mm:ss";
				SimpleDateFormat simple = new SimpleDateFormat(pattern);
				url = prestashopHost + "/api/orders?output_format=XML&filter[id]=[" + i + "," + (i + 100)
						+ "]&display=full&filter[date_upd]=<[" + simple.format(date) + "]&date=1";
			}
			if (api.equals("sync")) {
				url = prestashopHost + "/api/orders/" + i + "?output_format=XML";
			}
			RestTemplate restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(0, new Jaxb2RootElementHttpMessageConverter());
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setBasicAuth(prestashopApiToken, "");
			HttpEntity request = new HttpEntity(httpHeaders);
			try {
				ResponseEntity<Prestashop> response = restTemplate.exchange(url, HttpMethod.GET, request,
						Prestashop.class);

				if (response.getStatusCode() == HttpStatus.OK) {
					if (response.getBody() != null) {
						logger.info("Order details body" + response.getBody());
						prestashop = response.getBody();
						return prestashop;
					}
				}
			} catch (Exception ex) {
				logger.error("Could not fetch the data for request:  " + url);
				throw new Exception("There might be a network failure while fetching orders. Exception is: " + ex);
			}
		} catch (Exception e) {
			logger.error("Failed while getting the orders");
			throw new RuntimeException(e);
		}
		return null;
	}

	@Transactional
	private void savePrestashopOrders(Prestashop prestashop) {

		List<PrestashopOrdersDTO> prestashopOrderDTOList = new ArrayList<>();
		List<PrestashopOrders> prestashopOrdersList = new ArrayList<>();
		PrestashopOrders prestashopOrders = null;
		List<PrestashopOrderProducts> prestashopProductsList = null;
		PrestashopOrderProducts prestashopOrderProducts = null;
		try {
			Map<Integer, String> map = impl.getOrderStates();
			if (!prestashop.getPrestashopOrdersList().isEmpty()) {
				prestashopOrderDTOList = prestashop.getPrestashopOrdersList();
				for (PrestashopOrdersDTO orders : prestashopOrderDTOList) {
					try {
						prestashopOrders = new PrestashopOrders();
						prestashopProductsList = new ArrayList<PrestashopOrderProducts>();
						prestashopOrders.setPrestashopOrderId(orders.getId());
						prestashopOrders.setOrderCurrentState(Integer.valueOf(orders.getCurrentState()));
						prestashopOrders.setProcessed(map.get(Integer.valueOf(orders.getCurrentState())));
						prestashopOrders.setReference(orders.getReference());
						prestashopOrders.setDeliveryDate(orders.getDeliveryDate().equals("0000-00-00 00:00:00") ? null
								: Timestamp.valueOf(orders.getDeliveryDate()));
						prestashopOrders.setDateOrderAdded(orders.getDateAdd().equals("0000-00-00 00:00:00") ? null
								: Timestamp.valueOf(orders.getDateAdd()));
						prestashopOrders.setDateOrderUpdated(orders.getDateUpd().equals("0000-00-00 00:00:00") ? null
								: Timestamp.valueOf(orders.getDateUpd()));
						prestashopOrders.setOrderTotalPriceTaxExcl(orders.getTotalPaidTaxExcl());
						prestashopOrders.setOrderTotalPriceTaxIncl(orders.getTotalPaidTaxIncl());
						prestashopOrders.setAddressId(orders.getIdAddressDelivery().toString());
						PrestashopCustomer customer = new PrestashopCustomer();
						customer.setId(orders.getIdCustomer());
						prestashopOrders.setCustomer(customer);
					} catch (NullPointerException ex) {
						throw new NullPointerException(
								"Null pointer exception has occured while saving the orders in class "
										+ ApiScheduler.class);
					}
					saveCustomer(orders.getIdCustomer());
					for (PrestashopOrderRowDTO por : orders.getPrestashopOrderAssociationDTO()
							.getPrestashopOrderRow()) {
						prestashopOrderProducts = new PrestashopOrderProducts();
						prestashopOrderProducts.setProductId(por.getProductId());
						prestashopOrderProducts.setProductName(por.getProductName());
						prestashopOrderProducts.setProductDetailId(por.getId());
						prestashopOrderProducts.setOrder(prestashopOrders);
						prestashopOrderProducts.setProductQuantity(por.getProductQuantity());
						prestashopOrderProducts.setProducdPriceTaxIncl(por.getUnitPriceTaxIncl());
						prestashopOrderProducts.setProductPrice(por.getProductPrice());
						prestashopOrderProducts.setProductCostPrice(prestashopOrderDetailServiceImpl
								.getOrderDetails(por.getId()).getOriginalWholesalePrice());
						// prestashopOrderProducts.setProductCostPrice(12.2f);
						prestashopProductsList.add(prestashopOrderProducts);
					}
					prestashopOrders.setOrderProducts(prestashopProductsList);
					prestashopOrdersList.add(prestashopOrders);
				}
				prestashopOrderRepo.saveAll(prestashopOrdersList);
			}
			System.out.println(prestashopOrderDTOList);

		} catch (ArrayIndexOutOfBoundsException ex) {

			prestashopOrders.setProcessed("Incomplete Data");
			prestashopOrderRepo.saveAll(prestashopOrdersList);
			logger.error("ArrayIndexOutOfBound exception" + ex);
			throw new ArrayIndexOutOfBoundsException(
					"Error occured while inserting the the order in class " + ApiScheduler.class);
		} catch (JDBCException ex) {

			prestashopOrderRepo.saveAll(prestashopOrdersList);
			logger.error("SQLException exception" + ex);
			throw new JDBCException("Sql exception has occured while saving the orders in class: " + ApiScheduler.class,
					null);
		} catch (NullPointerException ex) {

			prestashopOrderRepo.saveAll(prestashopOrdersList);
			logger.error("Null pointer exception" + ex);
			throw new NullPointerException("Null pointer exception in " + ApiScheduler.class);
		}
	}

	public void saveCustomer(Integer id) {

		CustomerDTO customer = new CustomerDTO();
		PrestashopCustomer prestashopCustomer = null;
		try {
			customer = customerService.getCustomer(id);
			if (customer != null) {
				prestashopCustomer = new PrestashopCustomer();
				prestashopCustomer.setActiveFlag(customer.getActive());
				if (customer.getCompany() != null) {
					prestashopCustomer.setCompany(customer.getCompany());
				}
				prestashopCustomer.setId(customer.getId());
				prestashopCustomer.setDateAdd(Timestamp.valueOf(customer.getDateAdd()));
				prestashopCustomer.setDateUpd(Timestamp.valueOf(customer.getDateUpd()));
				if (customer.getEmail() != null) {
					prestashopCustomer.setEmail(customer.getEmail());
				}
				if (customer.getIdDefaultGroup() != null) {
					prestashopCustomer.setGroupId(customer.getIdDefaultGroup());
				}
				if (customer.getSiret() != null) {
					prestashopCustomer.setSiret(customer.getSiret());
				}
				prestashopCustomer.setFirstName(customer.getFirstName());
				prestashopCustomer.setLastName(customer.getLastName());
				if (customer.getWebsite() != null) {
					prestashopCustomer.setWebsite(customer.getWebsite());
				}
				customerRepo.save(prestashopCustomer);
			} else {
				System.out.println("Customer with id: " + id + " not found");
			}
		} catch (Exception ex) {			
			logger.info("Orders could not be updated in api scheduler");
			throw new RuntimeException(ex);
			
		}

	}

	@Scheduled(cron = "${updateApi.interval}")
	public List<Product> getPrestashopProductImageLink() {	
		try {
			List<Product> products = productRepo.findByImageUrlIsNull();
			if(products != null && !products.isEmpty()) {
				url = "/api/products?display=[id, id_default_image]&output_format=XML&filter[id]=[";
				String ids = "";
				Map<Integer, Product> productMap = new HashMap<>();
				for(Product product : products) {				
					if(product.getPrestashopProductId() != null) {
						ids += product.getPrestashopProductId() + "|";
						productMap.put(product.getPrestashopProductId(), product);
					}
				}
				url += ids + "]";	
				try {
					
					Prestashop prestashop = api.getPrestaOrderDetails(url, "get");	
					if(prestashop.getProductsList() != null && !prestashop.getProductsList().isEmpty()) {				
						List<Product> productsList = new ArrayList<>();
						for(PrestashopProductDto dto : prestashop.getProductsList()) {
							if (dto.getIdDefaultImage() != null && !dto.getIdDefaultImage().isEmpty()) {
								String imageUrl = prestashopHost + "/api/images/products/" + dto.getId() + "/"
										+ dto.getIdDefaultImage();
								Product product = productMap.get(dto.getId());
								product.setId(productMap.get(dto.getId()).getId());
								product.setImageUrl(imageUrl);
								productsList.add(product);
							}
						}
						try {
							
							productRepo.saveAll(productsList);
							return productsList;
						} catch (Exception e) {
							logger.error("Error occured while updating the product images");
							throw new RuntimeException(e);
						}						
					}
				} catch (Exception e) {
					logger.error("Error while fetching the images from prestashop");
					throw new RuntimeException(e);
				}							
			}							
			return null;
		} catch (Exception e) {
			logger.error("Error while fetching the products with image value null");
			throw new RuntimeException(e);
		}		
	}
}