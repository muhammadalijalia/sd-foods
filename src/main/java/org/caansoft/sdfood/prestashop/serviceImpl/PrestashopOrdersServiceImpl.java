package org.caansoft.sdfood.prestashop.serviceImpl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.caansoft.core.dto.EmployeeDto;
import org.caansoft.core.helper.Converter;
import org.caansoft.core.helper.GenericConvertor;
import org.caansoft.core.model.Employee;
import org.caansoft.core.model.User;
import org.caansoft.core.service.UserService;
import org.caansoft.sdfood.dto.StatsDto;
import org.caansoft.sdfood.enums.ModelDtoConvertor;
import org.caansoft.sdfood.helper.GenericConverter;
import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderProductsDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderRowDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrdersDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopProductSalesDTO;
import org.caansoft.sdfood.prestashop.dto.ProductExpiryDetailDTO;
import org.caansoft.sdfood.prestashop.model.PrestashopOrderProducts;
import org.caansoft.sdfood.prestashop.model.PrestashopOrders;
import org.caansoft.sdfood.prestashop.service.PrestashopOrderStateService;
import org.caansoft.sdfood.prestashop.service.PrestashopOrdersService;
import org.caansoft.sdfood.prestashopIntegration.ApiIntegration;
import org.caansoft.sdfood.prestashopIntegration.Prestashop;
import org.caansoft.sdfood.repo.PrestashopOrderProductsRepository;
import org.caansoft.sdfood.repo.PrestashopOrdersRepository;
import org.caansoft.sdfood.repo.ProductExpiryDetailRepo;
import org.caansoft.sdfood.repo.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PrestashopOrdersServiceImpl implements PrestashopOrdersService {

	private static Logger logger = LoggerFactory.getLogger(PrestashopOrdersServiceImpl.class);

	@Autowired
	private PrestashopOrdersRepository prestashopOrderRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private PrestashopOrderProductsRepository prestashopOrderProductRepo;
	
	@Autowired
	private ProductExpiryDetailRepo expiryRepo;
	
	@Autowired
	private UserService userService;

	@Autowired
	public PrestashopOrderStateService impl;

	Prestashop prestashop;	
	
	@Autowired
	public ApiIntegration api;

	@Override
	public Page<PrestashopOrdersDTO> find(Pageable pageable) {

//		Page<PrestashopOrders> prestashopOrders = prestashopOrderRepo.findAll(pageable);
//		List<PrestashopOrdersDTO> prestashopOrdersDTOList = new ArrayList<PrestashopOrdersDTO>();
//		PrestashopOrdersDTO prestashopOrdersDTO;
//		List<PrestashopOrderRowDTO> prestashopOrderRowDTOList;
//		PrestashopOrderRowDTO prestashopOrderRowDTO;
//		PrestashopOrderAssociationDTO prestashopOrderAssociationDTO;
//		if (prestashopOrders.getSize() > 0) {
//			for (PrestashopOrders orders : prestashopOrders) {
//				prestashopOrdersDTO = new PrestashopOrdersDTO();
//				prestashopOrderRowDTOList = new ArrayList<PrestashopOrderRowDTO>();
//				prestashopOrderAssociationDTO = new PrestashopOrderAssociationDTO();
//				prestashopOrdersDTO.setId(orders.getPrestashopOrderId());
//				prestashopOrdersDTO
//						.setDeliveryDate(orders.getDeliveryDate() == null ? null : orders.getDeliveryDate().toString());
//				prestashopOrdersDTO
//						.setDateAdd(orders.getDateOrderAdded() == null ? null : orders.getDateOrderAdded().toString());
//				prestashopOrdersDTO.setDateUpd(
//						orders.getDateOrderUpdated() == null ? null : orders.getDateOrderUpdated().toString());
//				prestashopOrdersDTO.setTotalPaidTaxIncl(orders.getOrderTotalPriceTaxIncl());
//				prestashopOrdersDTO.setTotalPaidTaxExcl(orders.getOrderTotalPriceTaxExcl());
//				prestashopOrdersDTO.setReference(orders.getReference());
//				prestashopOrdersDTO.setCurrentState(orders.getOrderCurrentState());
//				for (PrestashopOrderProducts pop : orders.getOrderProducts()) {
//					prestashopOrderRowDTO = new PrestashopOrderRowDTO();
//					prestashopOrderRowDTO.setProductId(pop.getProductId());
//					prestashopOrderRowDTO.setProductName(pop.getProductName());
//					prestashopOrderRowDTO.setUnitPriceTaxIncl(pop.getProducdPriceTaxIncl());
//					prestashopOrderRowDTO.setUnitPriceTaxExcl(pop.getProductPrice());
//					prestashopOrderRowDTO.setProductQuantity(pop.getProductQuantity());
//					prestashopOrderRowDTO.setId(pop.getProductDetailId());
//					prestashopOrderRowDTOList.add(prestashopOrderRowDTO);
//				}
//				prestashopOrderAssociationDTO.setPrestashopOrderRow(prestashopOrderRowDTOList);
//				prestashopOrdersDTO.setPrestashopOrderAssociationDTO(prestashopOrderAssociationDTO);
//				prestashopOrdersDTOList.add(prestashopOrdersDTO);
//			}
//		}
//		Page<PrestashopOrdersDTO> page = new PageImpl<PrestashopOrdersDTO>(prestashopOrdersDTOList, pageable,
//				prestashopOrders.getTotalElements());
//		return page;

		return null;
	}

//	@Override
//	public List<PrestashopOrderSalesDTO> findOrdersByDate(String startDate, String endDate) {
//
//		List<PrestashopOrders> prestashopOrders = prestashopOrderRepo.findByDateOrderAddedIsBetween(
//				Timestamp.valueOf(new String("2022-04-07 00:00:00")),
//				Timestamp.valueOf(new String("2022-04-13 24:59:59")));
//		List<PrestashopOrderSalesDTO> prestashopOrderSalesDTOList = new ArrayList<PrestashopOrderSalesDTO>();
//		PrestashopOrderSalesDTO prestashopOrderSalesDTO = new PrestashopOrderSalesDTO();
//		Float orderSales = (float) prestashopOrders.stream().mapToDouble(s -> s.getOrderTotalPriceTaxIncl()).sum();
//		Integer orderSalesQuantity = prestashopOrders.size();
//		prestashopOrderSalesDTO.setId(1);
//		prestashopOrderSalesDTO.setOrderSales(orderSales);
//		prestashopOrderSalesDTO.setOrdersQuantity(orderSalesQuantity);
//		prestashopOrderSalesDTOList.add(prestashopOrderSalesDTO);
//		return prestashopOrderSalesDTOList;
//	}

	@Override
	public Page<PrestashopProductSalesDTO> findProductSales(Long startDate, Long endDate, String productName,
			Pageable pageable, Boolean isPageable) {

		final DecimalFormat df = new DecimalFormat("0.00");
		int i = prestashopOrderRepo.findLastRecordId();
		System.out.println(i);
		try {
			PrestashopProductSalesDTO prestashopProductSalesDTO;
			PrestashopOrders po;
			List<PrestashopProductSalesDTO> prestashoProductSalesDTOList = new ArrayList<PrestashopProductSalesDTO>();
			Timestamp sDate = null;
			Timestamp eDate = null;
			if (startDate == null || endDate == null) {
				po = prestashopOrderRepo.findStartandEndDate();
				sDate = startDate == null ? new Timestamp(po.getStartDate().getTime()) : new Timestamp(startDate);
				eDate = endDate == null ? new Timestamp(po.getEndDate().getTime()) : new Timestamp(endDate);
			} else {
				sDate = new Timestamp(startDate);
				eDate = new Timestamp(endDate);
			}

			Page<PrestashopOrderProducts> prestashopOrderProductsList = prestashopOrderProductRepo
					.findProductsBySales(sDate, eDate, productName, !isPageable ? Pageable.unpaged() : pageable);
			for (PrestashopOrderProducts orderProducts : prestashopOrderProductsList) {

				prestashopProductSalesDTO = new PrestashopProductSalesDTO();
				prestashopProductSalesDTO.setId(orderProducts.getProductId());
				prestashopProductSalesDTO.setProductName(orderProducts.getProductName());
				prestashopProductSalesDTO.setProductTotalQuantity(orderProducts.getProductTotalQuantity().intValue());
				prestashopProductSalesDTO
						.setProductTotalSales(df.format(orderProducts.getProductTotalSales().floatValue()));
				prestashopProductSalesDTO.setProductTotalSalesTaxExcl(
						df.format(orderProducts.getProductTotalSalesTaxExcl().floatValue()));
				prestashopProductSalesDTO.setProductTaxValue(df.format(orderProducts.getTaxValue()));
				prestashopProductSalesDTO.setProductCostPrice(
						orderProducts.getTotalCostPrice() == null ? "0" : df.format(orderProducts.getTotalCostPrice()));
				prestashoProductSalesDTOList.add(prestashopProductSalesDTO);
			}
			Page<PrestashopProductSalesDTO> page = new PageImpl<PrestashopProductSalesDTO>(prestashoProductSalesDTOList,
					pageable, prestashopOrderProductsList.getTotalElements());
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public StatsDto findTotalOrdersAndAmount(Date startDate, Date endDate) {

		PrestashopOrders prestashopOrders;
		StatsDto statsDTO = new StatsDto();
		prestashopOrders = prestashopOrderRepo.findByDateOrderAddedIsBetween(new Timestamp(startDate.getTime()),
				new Timestamp(endDate.getTime()));
		statsDTO.setTotalOrders(prestashopOrders.getTotalOrders());
		statsDTO.setTotalSaleAmount(prestashopOrders.getTotalSalesAmount());
		return statsDTO;
	}

	@Override
	public List findOrdersByDate(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<PrestashopOrdersDTO> findOrders(String id, String referenceNo, String productName, Long startDate, Long endDate,
			String status, String url, Long empId, Pageable pageable, Boolean isPageable) {

		List<Integer> ids = null; 
		Boolean isFilterOnDateUpdated = false;
		if(url != null && url.equals("/pickOrder")) {
			ids = new ArrayList<Integer>();
			ids.add(37);
			ids.add(38);			
		}
		if(url != null && url.equals("/orderToDeliver")) {
			ids = new ArrayList<Integer>();
			ids.add(27);
			ids.add(30);
		}
		if (url !=null && ( url.equals("/pickOrder") || url.equals("/orderToDeliver")) ) {
			isFilterOnDateUpdated = true;
		}
		if(id != null) {				
			if(id.equals("13")) {
				ids = new ArrayList<Integer>();
				ids.add(26);
				ids.add(29);
				ids.add(35);
				ids.add(33);
			}
			if(id.equals("9")) {
				ids = new ArrayList<Integer>();
				ids.add(34);
				ids.add(36);							
			}
		}
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedInUser = userService.findUser(username);		
		if (loggedInUser != null) {
			if (loggedInUser.getEmployee().getJob().getId() == Long.valueOf(16)) {
				empId = loggedInUser.getEmployee().getId();
			}
		}
		Page<PrestashopOrders> prestashopOrders = prestashopOrderRepo
				.findAll(getOrderSpecs(ids, referenceNo, productName, startDate, endDate, status, empId, isFilterOnDateUpdated), !isPageable ? Pageable.unpaged() : pageable);
//		Map<Integer, String> map = impl.getOrderStates();

		List<PrestashopOrdersDTO> prestashopOrdersDTOList = new ArrayList<PrestashopOrdersDTO>();
		for (PrestashopOrders po : prestashopOrders) {
			PrestashopOrdersDTO prestashopOrdersDTO = new PrestashopOrdersDTO();
			prestashopOrdersDTO.setId(po.getPrestashopOrderId());
			prestashopOrdersDTO.setDateAdd(po.getDateOrderAdded().toString());
			prestashopOrdersDTO.setDateUpd(po.getDateOrderUpdated().toString());
			prestashopOrdersDTO.setDeliveryDate(
					po.getDeliveryDate() == null ? null : String.valueOf(po.getDeliveryDate().getDate()));
			prestashopOrdersDTO.setCurrentState(po.getOrderCurrentState().toString());
			prestashopOrdersDTO.setTotalPaidTaxIncl(po.getOrderTotalPriceTaxIncl());
			prestashopOrdersDTO.setTotalPaidTaxExcl(po.getOrderTotalPriceTaxExcl());
			prestashopOrdersDTO.setReference(po.getReference());
			prestashopOrdersDTO.setProcessed(po.getProcessed());
			prestashopOrdersDTO.setGiftMessage(po.getProcessed());	
			if(po.getRider() != null) {
				try {
					prestashopOrdersDTO.setRider(GenericConvertor.EMPLOYEE.modelToDto(po.getRider(), EmployeeDto.class));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (po.getCustomer() != null) {
				if (po.getCustomer().getFirstName() != null && po.getCustomer().getLastName() != null) {
					prestashopOrdersDTO
							.setClientName(po.getCustomer().getFirstName() + " " + po.getCustomer().getLastName());
				}
				if(po.getCustomer().getCompany() != null) {
					prestashopOrdersDTO.setClientCompany(po.getCustomer().getCompany());
				}
			}
//			PrestashopOrderAssociationDTO prestashopOrderAssociationDTO = new PrestashopOrderAssociationDTO();
//			List<PrestashopOrderRowDTO> prestashopOrderRowDTOList = new ArrayList<PrestashopOrderRowDTO>();
//			for(PrestashopOrderProducts pop : po.getOrderProducts()) {
//				PrestashopOrderRowDTO prestashopOrderRowDTO = new PrestashopOrderRowDTO();
//				prestashopOrderRowDTO.setProductId(pop.getProductId());
//				prestashopOrderRowDTO.setProductName(pop.getProductName());
//				prestashopOrderRowDTO.setUnitPriceTaxIncl(pop.getProducdPriceTaxIncl());
//				prestashopOrderRowDTO.setUnitPriceTaxExcl(pop.getProductPrice());
//				prestashopOrderRowDTO.setProductQuantity(pop.getProductQuantity());
//				prestashopOrderRowDTOList.add(prestashopOrderRowDTO);
//			}
//			prestashopOrderAssociationDTO.setPrestashopOrderRow(prestashopOrderRowDTOList);
//			prestashopOrdersDTO.setPrestashopOrderAssociationDTO(prestashopOrderAssociationDTO);
			prestashopOrdersDTOList.add(prestashopOrdersDTO);
		}

		Page<PrestashopOrdersDTO> page = new PageImpl<PrestashopOrdersDTO>(prestashopOrdersDTOList, pageable,
				prestashopOrders.getTotalElements());
		return page;
	}

	private Specification<PrestashopOrders> getOrderSpecs(List<Integer> ids, String referenceNo, String productName, Long startDate,
			Long endDate, String status, Long empId, Boolean filter) {

		return (Specification<PrestashopOrders>) (root, query, builder) -> {

			final List<Predicate> predicates = new ArrayList<>();

			if (referenceNo != null) {
				predicates.add(builder.like(root.get("reference"), "%" + referenceNo + "%"));
			}
			if (productName != null) {
				Join<PrestashopOrders, PrestashopOrderProducts> orderProductJoin = root.join("orderProducts");
				predicates.add(builder.like(orderProductJoin.get("productName"), "%" + productName + "%"));
			}			
			if (startDate != null) {
				predicates.add(builder.greaterThanOrEqualTo(builder.function("date", Date.class, !filter ? root.get("dateOrderAdded") : root.get("dateOrderUpdated")), new Timestamp(startDate)));
			}
			if (endDate != null) {
				predicates.add(builder.lessThanOrEqualTo(builder.function("date", Date.class, !filter ? root.get("dateOrderAdded") : root.get("dateOrderUpdated")), new Timestamp(endDate)));
			}						
			if(ids != null) {
				predicates.add(builder.in(root.get("orderCurrentState")).value(ids));
			}
			if (status != null) {
				predicates.add(builder.like(root.get("processed"), "%" + status + "%"));
			}
			if (empId != null) {
				predicates.add(builder.equal(root.get("rider").<Long>get("id"), empId));
			}

			return builder.and(predicates.toArray(new Predicate[predicates.size()]));
		};

	}

	@Override
	public List<PrestashopOrderProductsDTO> findDistinctProducts() {

		List<String> productList = new ArrayList<>();
		List<PrestashopOrderProductsDTO> productDTOList = new ArrayList<>();
		productList = prestashopOrderProductRepo.findDistinctProductName();
		for (String str : productList) {
			PrestashopOrderProductsDTO productDTO = new PrestashopOrderProductsDTO();
//			productDTO.setId(pop.getProductId());
			productDTO.setName(str);
			productDTOList.add(productDTO);
		}
		return productDTOList;
	}

	@Override
	public Page<PrestashopOrderRowDTO> findOrderById(Integer id, Pageable pageable) {

		PrestashopOrders po = new PrestashopOrders();
		po.setPrestashopOrderId(id);
		Page<PrestashopOrderProducts> prestOrderProducts = prestashopOrderProductRepo.findAllByOrder(po, pageable);
//		List<PrestashopOrderProducts> prestOrderProducts = prestashopOrderProductRepo.findAllByOrder(po, pageable);
		List<PrestashopOrderProducts> orderProductsList = prestOrderProducts.toList();
		List<Integer> productList = new ArrayList<Integer>();
		for (PrestashopOrderProducts value : orderProductsList) {
			productList.add(value.getProductId());
		}
		Map<Integer, Product> products = new HashMap<Integer, Product>();
		products = productRepo.findByPrestashopProductIdMap(productList);
		List<PrestashopOrderRowDTO> popList = new ArrayList<>();
		for (PrestashopOrderProducts pop : prestOrderProducts) {
			PrestashopOrderRowDTO productDTO = new PrestashopOrderRowDTO();
			Product product = products.get(pop.getProductId());
			if(product != null) {
				productDTO.setExpiry(product.getIsExpiry());
				productDTO.setLotNumber(product.getIsFresh());
				productDTO.setId(product.getPrestashopProductId());
				productDTO.setSystemProductId(product.getId());
				if(product.getImageUrl() != null) {										
					productDTO.setImageUrl(product.getImageUrl());
				}
			} else {
				productDTO.setId(pop.getProductId());
			}
			productDTO.setCostPrice(pop.getProductCostPrice());
			productDTO.setProductId(pop.getProductId());
			productDTO.setProductPrice(pop.getProductPrice());
			productDTO.setProductName(pop.getProductName());
			productDTO.setProductQuantity(pop.getProductQuantity());
			productDTO.setUnitPriceTaxIncl(pop.getProducdPriceTaxIncl());
			popList.add(productDTO);
		}
		Page<PrestashopOrderRowDTO> page = new PageImpl<PrestashopOrderRowDTO>(popList, pageable,
				prestOrderProducts.getTotalElements());
		return page;
	}

	@Override
	public PrestashopOrdersDTO findPrestashopOrderById(Integer id) {

		PrestashopOrders order = new PrestashopOrders();
		try {
			order = prestashopOrderRepo.findByPrestashopOrderId(id);
			if (order != null) {
				PrestashopOrdersDTO dto = new PrestashopOrdersDTO();
				//update address id for previous orders
				if(order.getAddressId() == null) {
					String url = "/api/orders/" + id + "?output_format=XML";
					Prestashop prestashop = api.getPrestaOrderDetails(url, "get");
					order.setAddressId(String.valueOf(prestashop.getPrestashopOrderDetails().getIdAddressDelivery()));
					prestashopOrderRepo.save(order);
					dto.setIdAddressDelivery(prestashop.getPrestashopOrderDetails().getIdAddressDelivery());
				}
				dto.setProcessed(order.getProcessed());
				dto.setId(order.getPrestashopOrderId());
				dto.setReference(order.getReference());
				dto.setDateAdd(String.valueOf(order.getDateOrderAdded()));
				dto.setUpdatedBy(order.getUpdatedBy());
				dto.setUpdatedDate(String.valueOf(order.getSdfoods_date_upd()));
				dto.setCurrentState(String.valueOf(order.getOrderCurrentState()));
				dto.setIdCustomer(order.getCustomer() != null ? order.getCustomer().getId() : null);
				dto.setIdAddressDelivery(order.getAddressId() != null ? Integer.valueOf(order.getAddressId()) : null);
				if(order.getRider() != null){
					try {
						dto.setRider(GenericConvertor.EMPLOYEE.modelToDto(order.getRider(), EmployeeDto.class));
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return dto;
			}

		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	@Override
	public ProductExpiryDetailDTO deliveryProducts(Integer id) {
		
//		List<ProductExpiryDetail> list = expiryRepo.findAllProductIdAndQuantityPreparedAndOrderIdByOrderId(id);
		
		
						
//		System.out.println(list);
		System.out.println("response");
		return null;
	}

	@Override
	public PrestashopOrders assignOrderToRider(Integer orderId, Long empId) {
		
		PrestashopOrders order = prestashopOrderRepo.findByPrestashopOrderId(orderId);
		if(order != null) {
			Employee emp = new Employee();
			emp.setId(empId);
			order.setRider(emp);
			prestashopOrderRepo.save(order);
			return order;
		}
		return null;
	}
	
	public Object getProductImage (Long id) {
		
		String url = "/api/images/products/23/65";
		Prestashop prestashop = api.getPrestaOrderDetails(url, "image");
		return null;
	}

	@Override
	public List<String> orderStatuses() {
		
		List<String> list = prestashopOrderRepo.findDistinctProcessed();
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

//	@Override
//	public List<String> getPrestashopOrderStatuses() {
//		
//		List<String> statuses = prestashopOrderRepo.findDistinctProcessed();
//		if(!statuses.isEmpty()) {
//			return statuses;
//		}
//		return null;
//	}
}
