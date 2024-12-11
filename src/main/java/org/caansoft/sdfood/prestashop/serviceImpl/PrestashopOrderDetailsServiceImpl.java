package org.caansoft.sdfood.prestashop.serviceImpl;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.dto.ProductDto;
import org.caansoft.sdfood.dto.ProductLocationDto;
import org.caansoft.sdfood.enums.ProductStockTransactionType;
import org.caansoft.sdfood.model.OrderProduct;
import org.caansoft.sdfood.model.OrderProductLocation;
import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.model.ProductStock;
import org.caansoft.sdfood.prestashop.dto.ExpiriesDTO;
import org.caansoft.sdfood.prestashop.dto.ExpiryDTO;
import org.caansoft.sdfood.prestashop.dto.MessageDTO;
import org.caansoft.sdfood.prestashop.dto.PrestashopOrderDetailsDTO;
import org.caansoft.sdfood.prestashop.dto.ProductExpiryDetailDTO;
import org.caansoft.sdfood.prestashop.model.PrestashopOrderProducts;
import org.caansoft.sdfood.prestashop.model.PrestashopOrderStatus;
import org.caansoft.sdfood.prestashop.model.PrestashopOrders;
import org.caansoft.sdfood.prestashop.model.ProductExpiryDetail;
import org.caansoft.sdfood.prestashop.service.PrestashopOrderDetailService;
import org.caansoft.sdfood.prestashopIntegration.ApiIntegration;
import org.caansoft.sdfood.prestashopIntegration.Prestashop;
import org.caansoft.sdfood.repo.OrderProductLocationRepo;
import org.caansoft.sdfood.repo.OrderProductRepository;
import org.caansoft.sdfood.repo.PrestashopOrderProductsRepository;
import org.caansoft.sdfood.repo.PrestashopOrderStatusRepository;
import org.caansoft.sdfood.repo.PrestashopOrdersRepository;
import org.caansoft.sdfood.repo.ProductExpiryDetailRepo;
import org.caansoft.sdfood.repo.ProductRepository;
import org.caansoft.sdfood.repo.ProductStockRepository;
import org.hibernate.JDBCException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;


import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PrestashopOrderDetailsServiceImpl implements PrestashopOrderDetailService {

    private static Logger logger = LoggerFactory.getLogger(PrestashopOrderDetailsServiceImpl.class);

    @Autowired
    private ApiIntegration api;

    @Autowired
    private OrderProductLocationRepo locationRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductExpiryDetailRepo expiryRepo;

    @Autowired
    private PrestashopOrdersRepository orderRepo;

    @Autowired
    private PrestashopOrderStatusRepository orderStatusRepo;
    
    @Autowired
    private PrestashopOrderProductsRepository prestaOrderProductRepo;
    
    @Autowired
    private OrderProductRepository orderProductRepo;
    
    @Autowired
    private ProductStockRepository productStockRepo;

    String createdBy;
    Timestamp createdOn;

    @Override
    public PrestashopOrderDetailsDTO getOrderDetails(Integer id) {

        PrestashopOrderDetailsDTO prestashopOrderDetailsDTO = new PrestashopOrderDetailsDTO();
        String url = "/api/order_details/" + id + "?output_format=XML";
        Prestashop prestashop = api.getPrestaOrderDetails(url, "get");
        if (prestashop != null && prestashop.getPrestashopOrderDetailsDTO() != null) {

            prestashopOrderDetailsDTO = prestashop.getPrestashopOrderDetailsDTO();
            return prestashopOrderDetailsDTO;
        }
        return null;
    }

    public Prestashop getOrderDetailList(List<Integer> ids) {
        StringBuilder sb = new StringBuilder();
        ids.forEach(detailId -> {
                    sb.append(String.valueOf(detailId));
                    sb.append("|");
                }

        );
        sb.deleteCharAt(sb.lastIndexOf("|"));        
        String url = "/api/order_details?filter[id]=[" + sb.toString() + "]&display=full&output_format=XML";
        Prestashop prestashop = api.getPrestaOrderDetails(url, "get");
        return prestashop;
    }

    @Transactional
    public void confirmOrder(Integer id) {

        List<ProductExpiryDetail> expiryDetails = new ArrayList<ProductExpiryDetail>();
        try {
            expiryDetails = expiryRepo.findByOrderId(id);
            if (expiryDetails != null) {
                createdBy = expiryDetails.get(0).getCreatedBy();
                createdOn = expiryDetails.get(0).getCreatedOn();
            }

            List<ProductExpiryDetail> invalidExps = expiryDetails.stream().map(o -> {
                        o.setFlag(Flag.INACTIVE);
                        return o;
                    }
            ).collect(Collectors.toList());
            expiryRepo.saveAll(invalidExps);
        } catch (JDBCException ex) {

            logger.error("SQLException exception" + ex);
            throw new JDBCException("Sql exception has occured while saving the orders in class: "
                    + PrestashopOrderDetailsServiceImpl.class, null);
        } catch (NullPointerException ex) {

            logger.error("Null pointer exception" + ex);
            throw new NullPointerException("Null pointer exception in " + PrestashopOrderDetailsServiceImpl.class);
        }
    }

    /**
     * Below description by usmanjaved
     * Non réglée - Traitement en cours = id 26
     * or
     * Réglée - Traitement en cours = id 29
     * <p>
     * <p>
     * then switch to :
     * Non réglée - En cours de préparation = 33
     * or
     * Réglée - En cours de préparation = 35
     * when order is prepare then switch to :
     * Non réglée - Commande prête = 34
     * or
     * Réglée - Commande prête = 36
     * <p>
     * when order is controlled then switch to :
     * Non réglée - Commande prête pour envoi = id 38
     * or
     * Réglée - Commande prête pour envoi = id 37
     */
    private static Map<String, String> stateMap = new HashMap<>();

    {
        stateMap.put("26", "33");
        stateMap.put("29", "35");
        stateMap.put("33", "34");
        stateMap.put("35", "36");
        stateMap.put("34", "38");
        stateMap.put("36", "37");
        stateMap.put("38", "27");
        stateMap.put("37", "30");
        stateMap.put("27", "31");
        stateMap.put("30", "32");
    }

    @Override
    public Object update(ProductExpiryDetailDTO expiry, String orderState) {

        logger.info(String.valueOf(expiry));
        logger.info("This is order state  :::  " + orderState);
        HttpStatus response = null;
        try {
            if (expiry != null) {
                String url = "/api/orders/" + expiry.getOrderId() + "?output_format=XML";
                PrestashopOrders order = orderRepo.findByPrestashopOrderId(expiry.getOrderId());

                if (order == null) {
                    logger.info("Couldn't find prestashop order in the db: orderId " + expiry.getOrderId());
                    throw new RuntimeException("Couldn't find prestashop order in the db: orderId " + expiry.getOrderId());
                }
                Prestashop obj = api.getPrestaOrderDetails(url, "get");

                if (obj == null) {
                    logger.info("Couldn't find prestashop order in the presta: orderId " + expiry.getOrderId());
                    throw new RuntimeException("Couldn't find prestashop order in the presta: orderId " + expiry.getOrderId());
                }

                logger.info("object from API prestashop: gift: " + obj.getPrestashopOrderDetails().getGift());
                logger.info("object from API prestashop: mobile theme: "
                        + obj.getPrestashopOrderDetails().getMobileTheme());


                if (obj != null) {
                    String currentState = String.valueOf(order.getOrderCurrentState());
                    Prestashop orderStates  = api.getStatusDetail("/api/order_states/" + stateMap.get(currentState) + "?output_format=XML");

                    if (orderStates.getPrestashopOrderState() == null) {
                        logger.info("Couldn't find prestashop order status detail: stateId " + currentState);
                        throw new RuntimeException("Couldn't find prestashop order status detail: stateId " + currentState);
                    }

                    obj.getPrestashopOrderDetails().setCurrentState(stateMap.get(currentState));
                    obj.getPrestashopOrderDetails().setGift(null);
                    obj.getPrestashopOrderDetails().setMobileTheme(null);

                    response = api.updaatePrestaOrder(url, "put", obj);

                    if (response == null) {
                        logger.info("Couldn't update prestashop order: orderId " + expiry.getOrderId());
                        throw new RuntimeException("Couldn't update prestashop order: orderId " + expiry.getOrderId());
                    }

                    if (response == HttpStatus.OK && orderState != null
                            && (orderState.equals("confirm") || orderState.equals("update"))) {

                        if (orderState.equals("confirm")) {
                            confirmOrder(expiry.getOrderId());
                        } //TODO preparator details should not be deleted

                        for (ProductDto dto : expiry.getProducts()) {
                            for (ExpiryDTO expiryDto : dto.getFields()) {
                                ProductExpiryDetail detail = new ProductExpiryDetail();
                                detail.setQuantityPrepared(expiryDto.getQuantity());
                                detail.setName(dto.getName());
                                detail.setOrderId(expiry.getOrderId());
                                detail.setProductId(dto.getId());
                                detail.setOrderQuantity(dto.getQuantity());
                                detail.setUpdatedBy(expiry.getUpdatedBy());
                                if (orderState.equals("confirm")) {
                                    detail.setCreatedOn(createdOn);
                                    detail.setCreatedBy(createdBy);
                                }

                                if (expiryDto.getExpiry() != null) {
                                    detail.setExpiry(expiryDto.getExpiry());
                                }
                                if (expiryDto.getLotNumber() != null) {
                                    detail.setLotNumber(expiryDto.getLotNumber());
                                }
                                expiryRepo.save(detail);
                            }
                        }
                    } else if (orderState.equals("delivery") && expiry.getMessage() != null && !expiry.getMessage().isEmpty()) {
                        url = "/api/messages";
                        MessageDTO message = new MessageDTO();
                        message.setIdOrder(expiry.getOrderId());
                        message.setMessage(expiry.getMessage());
//                        List<MessageDTO> messageDTOs = new ArrayList<>();
//                        messageDTOs.add(message);
                        response = api.postOrderMessage(url, message);

                        if (response == null) {
                            logger.info("Couldn't update prestashop message: orderId " + expiry.getOrderId());
                        }
                    }

                    order.setSdfoods_date_upd(Timestamp.valueOf(LocalDateTime.now()));
                    order.setUpdatedBy(expiry.getUpdatedBy());
                    order.setOrderCurrentState(Integer.valueOf(stateMap.get(currentState)));
                    order.setProcessed(orderStates.getPrestashopOrderState().getName() != null ? 
                    		orderStates.getPrestashopOrderState().getName().get(0).getLanguage().get(0).getName() : null);
                    order = orderRepo.save(order);  
                    
                    if (order != null && (order.getOrderCurrentState().equals(31) || order.getOrderCurrentState().equals(32))) {                
                    	List<ProductStock> ps = processedOrderProductsStock(order);
                    }

                    PrestashopOrderStatus orderStatus = new PrestashopOrderStatus();
                    orderStatus.setOrderId(expiry.getOrderId());
                    orderStatus.setStatusId(Integer.valueOf(stateMap.get(currentState)));
                    orderStatus.setStatusValue(orderStates.getPrestashopOrderState().getName() != null ? 
                    		orderStates.getPrestashopOrderState().getName().get(0).getLanguage().get(0).getName() : null);
                    if (expiry.getMessage() != null) {
                        orderStatus.setComments(expiry.getMessage());
                    }
                    orderStatusRepo.save(orderStatus);

                    return response;
                }
            }
        } catch (ResourceAccessException ex) {
            throw new ResourceAccessException("There is error while accessing the resource: " + response);
        } catch (Exception e) {
            logger.error("exception in update(): " + e.getMessage());
        }
        return null;
    }
    
    private List<ProductStock> processedOrderProductsStock(PrestashopOrders prestashopOrder) {
    	
    	List<ProductStock> psList;
    	if (prestashopOrder != null) {
    		try {
    			List<ProductExpiryDetail> pedList = expiryRepo.findByOrderId(prestashopOrder.getPrestashopOrderId());
    			if (pedList != null && !pedList.isEmpty()) { 
    				psList = new ArrayList<>();
    				for (ProductExpiryDetail ped : pedList) {
    					if (ped.getFlag().equals(Flag.ACTIVE)) {
    						ProductStock ps = new ProductStock();
        					ps.setOrderId(ped.getOrderId().longValue());
        					ps.setLotNumber(ped.getLotNumber());
        					ps.setExprityDate(ped.getExpiry());
        					ps.setQuantityOut(ped.getQuantityPrepared().intValue());
        					ps.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        					ps.setUpdatedOn(ped.getCreatedOn());
        					try {
        						Optional<Product> product = productRepo.findById(ped.getProductId());
        						if (product.isPresent()) {
        							ps.setPrestashopProductId(product.get().getPrestashopProductId().longValue());
        							ps.setProduct(product.get());
        						} else {
        							logger.info("Product not found");
        						}
        					} catch (Exception e) {
        						logger.error("Some error occured while retrieving the product for Id: " + ped.getProductId());
        						throw new RuntimeException(e);
        					}
        					ps.setExternalSystemName("SDFOODS");
        					ps.setTransactionType(ProductStockTransactionType.SOLD);
        					try {
        						ps = productStockRepo.save(ps);
        					} catch (Exception e) {
        						logger.error("Could not successfully save the the product stock for product: " + ped.getProductId() 
        							+ " of prestashop order: " + prestashopOrder.getPrestashopOrderId());
        						throw new RuntimeException(e);
        					}
        					psList.add(ps);        				
    					}
    				}
    			} else {
    				logger.info("Processed Order Products are not found for the prestashop order: " + prestashopOrder.getPrestashopOrderId());
    				return null;
    			}
    		} catch (Exception e) {
    			logger.error("Error occured while fetching the processed order products for prestashop order: " + prestashopOrder.getPrestashopOrderId());
    			throw new RuntimeException(e);
    		}
    	} else {
    		logger.info("Prestashop Order is null");
    		return null;
    	}
    	return psList;
    }

    @Override
    public List<ProductLocationDto> findProductExpiry(Long id) {

        List<OrderProductLocation> opl = new ArrayList<OrderProductLocation>();
        List<ProductLocationDto> dtoList = new ArrayList<ProductLocationDto>();
        Optional<Product> product = productRepo.findById((id));
        opl = locationRepo.findByProductId(product.get().getId());
        for (OrderProductLocation productLocation : opl) {
            ProductLocationDto dto = new ProductLocationDto();
            if (productLocation.getQuantitySold() == null
                    || !productLocation.getQuantitySold().equals(productLocation.getReceivedQuantity())) {
                dto.setId(productLocation.getId());
                dto.setExpiry(productLocation.getExpiry());
                dto.setLotNumber(productLocation.getLotNumber());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }
    
	@Override
	public List<ExpiriesDTO> getProductExpiries(List<Long> ids) {

		try {

			List<OrderProduct> orderProducts = orderProductRepo.findAllByProductIdIn(ids);
			if (orderProducts != null && !orderProducts.isEmpty()) {
				try {

					List<OrderProductLocation> oplList = locationRepo.findAllByOrderProductsIn(orderProducts);
					if (oplList != null && !oplList.isEmpty()) {
						List<ProductLocationDto> dtoList = new ArrayList<ProductLocationDto>();
						List<ExpiriesDTO> expiriesObjectList = new ArrayList<>();
						for (OrderProductLocation opl : oplList) {
							ProductLocationDto dto = new ProductLocationDto();
							if (opl.getQuantitySold() == null
									|| !opl.getQuantitySold().equals(opl.getReceivedQuantity())) {
								dto.setId(opl.getOrderProducts().getProduct().getId());
								dto.setExpiry(opl.getExpiry() != null ? opl.getExpiry() : null);
								dto.setLotNumber(opl.getLotNumber() != null ? opl.getLotNumber() : null);
								dtoList.add(dto);
							}
						}
						if (dtoList != null && !dtoList.isEmpty()) {
							Map<Long, List<ProductLocationDto>> productLocationDtoMap = dtoList.stream()
									.collect(Collectors.groupingBy(ProductLocationDto::getId));
							for (Map.Entry<Long, List<ProductLocationDto>> entry : productLocationDtoMap.entrySet()) {
								ExpiriesDTO dto = new ExpiriesDTO();
								dto.setProductId(entry.getKey());
								dto.setProductLocationDtoList(entry.getValue());
								expiriesObjectList.add(dto);
							}
							return expiriesObjectList;
						}
					}
				} catch (Exception e) {
					logger.error("Exception while fetching the lot number and expiries from order product locations");
					throw new RuntimeException(e);
				}
			}
			logger.info("Either order products or Order product location or dtoList is empty or null");
		} catch (Exception e) {
			logger.error("Exception while fetching the products from order products");
			throw new RuntimeException(e);
		}

		return null;
	}

    private Specification<OrderProductLocation> getStockDetails(String lotNumber, Timestamp expiry, Long productId) {

        return (Specification<OrderProductLocation>) (root, query, builder) -> {

            final List<Predicate> predicates = new ArrayList<>();

            if (lotNumber != null) {
                predicates.add(builder.equal(root.get("lotNumber"), lotNumber));
            }
            if (expiry != null) {
                predicates.add(builder.equal(root.get("expiry"), expiry));
            }
            if (productId != null) {
                predicates.add(builder.equal(root.get("productId"), productId));
            }

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };

    }

    @Override
    public ProductExpiryDetailDTO findOrderDetails(Integer id) {

        List<ProductExpiryDetail> detail = new ArrayList<ProductExpiryDetail>();
        try {
            detail = expiryRepo.findByOrderId(id); 
            if(detail.isEmpty()) {
            	PrestashopOrders order = new PrestashopOrders();
            	order.setPrestashopOrderId(id);
            	Page<PrestashopOrderProducts> productsPage = prestaOrderProductRepo.findAllByOrder(order, Pageable.unpaged());
            	List<PrestashopOrderProducts> products = productsPage.toList();
            	List<Integer> ids = products.stream().map(product -> product.getProductId()).distinct().collect(Collectors.toList());
            	Map<Integer, Product> map = null;
            	if(ids!=null && !ids.isEmpty()) {
            		List<Product> productsList = productRepo.findAllByPrestashopProductIdIn(ids);
            		map = productsList.stream().collect(Collectors.toMap(Product::getPrestashopProductId, Function.identity()));
            	}
            	if(!products.isEmpty()) {
            		ProductExpiryDetailDTO expiry = new ProductExpiryDetailDTO();
            		List<ProductDto> productList = new ArrayList<>();            		
            		for(PrestashopOrderProducts product : products) {   
            			ProductDto dto = new ProductDto();
            			List<ExpiryDTO> expiryDtoList = new ArrayList<>();
            			ExpiryDTO expiryDTO = new ExpiryDTO();
            			dto.setPrestashopProductId(product.getProductId());
            			dto.setImageUrl(map.get(product.getProductId()) != null && map.get(product.getProductId()).getImageUrl() != null ? map.get(product.getProductId()).getImageUrl() : null);
            			dto.setQuantity(product.getProductQuantity().floatValue());
            			dto.setName(product.getProductName());
            			expiryDTO.setQuantity(product.getProductQuantity().longValue());
            			expiryDTO.setFlag(Flag.ACTIVE);
            			expiryDtoList.add(expiryDTO);
            			dto.setFields(expiryDtoList);
            			productList.add(dto);
            		}  
            		expiry.setOrderId(id);
            		expiry.setProducts(productList);
            		expiry.setFake(true);
            		return expiry;
            	}
            }
            Map<Long, List<ExpiryDTO>> productMap = new HashMap<>();
            List<ExpiryDTO> list = null;
            ExpiryDTO expiryDTO = null;
            ProductDto productDto = null;
            List<ProductDto> productDtoList = new ArrayList<ProductDto>();
            ProductExpiryDetailDTO expiry = new ProductExpiryDetailDTO(); 
            List<Long> productIds = detail.stream().map(product -> product.getProductId()).distinct().collect(Collectors.toList());
            List<Product> products = productRepo.findAllById(productIds);
            Map<Long, Product> productImageMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));
            for (ProductExpiryDetail d : detail) {
//                if (d.getFlag() == Flag.INACTIVE)
//                    continue;
                if (productMap.containsKey(d.getProductId())) {
                    list = productMap.get(d.getProductId());
                } else {
                    list = new ArrayList<>();
                    productDto = new ProductDto();
                    productDto.setFields(list);
                    productDto.setPrestashopProductId(d.getProductId().intValue());
                    productDto.setImageUrl(productImageMap.get(d.getProductId()).getImageUrl() == null ? null : productImageMap.get(d.getProductId()).getImageUrl());
                    productDto.setName(d.getName());
                    productDto.setQuantity(d.getOrderQuantity());
                    productDtoList.add(productDto);
                }
                expiryDTO = new ExpiryDTO();
                expiryDTO.setExpiry(d.getExpiry());
                expiryDTO.setLotNumber(d.getLotNumber());
                expiryDTO.setQuantity(d.getQuantityPrepared());
                expiryDTO.setFlag(d.getFlag());
                list.add(expiryDTO);
                productMap.put(d.getProductId(), list);
            }
//            for (ProductDto pDto : productDtoList) {
//                pDto.setFields(productMap.get(pDto.getPrestashopProductId().longValue()));
//            }
            expiry.setOrderId(id);
            expiry.setProducts(productDtoList);
            return expiry;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}