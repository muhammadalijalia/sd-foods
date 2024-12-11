package org.caansoft.sdfood.enums;

import org.caansoft.core.common.NullAwareBeanUtilsBean;
import org.caansoft.core.dto.EmployeeDto;
import org.caansoft.core.dto.MediaDto;
import org.caansoft.core.dto.PartnerDto;
import org.caansoft.core.enums.Flag;
import org.caansoft.core.helper.Converter;
import org.caansoft.core.helper.GenericConvertor;
import org.caansoft.core.model.Employee;
import org.caansoft.core.model.Media;
import org.caansoft.core.model.Partner;
import org.caansoft.core.model.User;
import org.caansoft.core.service.UserService;
import org.caansoft.sdfood.dto.*;
import org.caansoft.sdfood.model.*;
import org.caansoft.sdfood.repo.OrderPhotoRepository;
import org.caansoft.sdfood.repo.OrderProductRepository;
import org.caansoft.sdfood.repo.OrderRepository;
import org.caansoft.sdfood.repo.StockCountRepository;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public enum ModelDtoConvertor implements Converter {

    STOCK_COUNT_LINEITEM {
        @Override
        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            StockCountLineItemDto o = super.modelToDto(model, clazz);
            StockCountLineItem lineItem = (StockCountLineItem) model;
            o.setProductName(lineItem.getProduct().getName());
            o.setProductId(lineItem.getProduct().getId());
            return (U) o;
        }

        @Override
        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            StockCountLineItemDto lineItemDto = (StockCountLineItemDto) dto;
            StockCountLineItem o = super.dtoToModel(dto, clazz);
            Product product = new Product();
            product.setId(lineItemDto.getProductId());
            o.setProduct(product);
            return (U) o;
        }


    },
    STOCK_COUNT {
        @Override
        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            StockCountHeaderDto o = super.modelToDto(model, clazz);
            StockCountHeader header = (StockCountHeader) model;

            if (header.getStockStatusHistories() != null) {
                StringBuilder comments = new StringBuilder("");
                header.getStockStatusHistories().forEach(history -> {
                    if (history.getComments() != null && !history.getComments().isEmpty()) {
                        comments.append(history.getComments());
                        comments.append("\n");
                    }
                });
            }

            if (header.getStockStatusHistories() != null) {
                StringBuilder comments = new StringBuilder("");
                List<StockStatusHistoryDto> statusHistoryList = new ArrayList<>();
                header.getStockStatusHistories().forEach(history -> {
                    StockStatusHistoryDto status = new StockStatusHistoryDto();
                    status.setStatus(history.getStatus());
                    status.setComments(history.getComments());
                    statusHistoryList.add(status);
                });
                o.setStockStatusHistoryDtos(statusHistoryList);
            }

            if (header.getStockCountLineItems() != null) {
                List<StockCountLineItemDto> lineItemsDto = header.getStockCountLineItems().stream().map(x -> {
                    try {
                        StockCountLineItemDto stockCountLineItemDto = (StockCountLineItemDto) STOCK_COUNT_LINEITEM.modelToDto(x, StockCountLineItemDto.class);
                        return stockCountLineItemDto;
                    } catch (Exception e) {
                    }
                    return null;
                }).filter(x -> x != null).collect(Collectors.toList());
                o.setStockCountLineItemDtos(lineItemsDto);
            }
            return (U) o;
        }

        @Override
        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            StockCountHeaderDto stockCountHeaderDto = (StockCountHeaderDto) dto;
            StockCountHeader o = super.dtoToModel(dto, clazz);
            StockCountHeader dbObj = null;

            if (stockCountHeaderDto.getId() != null) {
                Optional<StockCountHeader> dbOrderOpt = stockCountRepository.findById(stockCountHeaderDto.getId());

                if (dbOrderOpt.isPresent()) {
                    dbObj = dbOrderOpt.get();

                    NullAwareBeanUtilsBean.get().copyProperties(o, dbObj);
                    NullAwareBeanUtilsBean.get().copyProperties(o, stockCountHeaderDto);

                    if (stockCountHeaderDto.getStatus() != null && !dbObj.getStatus().equals(stockCountHeaderDto.getStatus())) {
                        List<StockStatusHistory> statusHistoryList = dbObj.getStockStatusHistories();
                        statusHistoryList.stream().filter(obj -> obj.getEndDate() == null).findFirst().
                                ifPresent(obj -> {
                                    obj.setEndDate(Timestamp.from(Instant.now()));
                                });

                        StockStatusHistory statusHistory = new StockStatusHistory();
                        statusHistory.setStockCountHeader(o);
                        statusHistory.setStatus(stockCountHeaderDto.getStatus());
                        statusHistory.setStartDate(Timestamp.from(Instant.now()));
                        statusHistoryList.add(statusHistory);
                        o.setStockStatusHistories(statusHistoryList);

                    }
                }
            }

            if (stockCountHeaderDto.getStatus() != null) {
                o.setStatus(stockCountHeaderDto.getStatus());
            }

            return (U) o;
        }

    },
    LOCATION {
        @Override
        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            Location location = super.dtoToModel(dto, clazz);
            return (U) location;
        }

        @Override
        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            Location location = (Location) model;
            LocationsDto locationDto = super.modelToDto(model, clazz);
            //			Warehouse warehouse = (Warehouse) model;
            if (location.getWarehouse() != null) {
                locationDto.setWarehouseName(location.getWarehouse().getName());
            }
            return (U) locationDto;
        }
    },
    WAREHOUSE {
        @Override
        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            Warehouse warehouse = super.dtoToModel(dto, clazz);
            WarehouseDto warehouseDto = (WarehouseDto) dto;

            if (warehouseDto.getLocationsList() != null) {
                List<Location> locationList = new ArrayList<>();
                for (LocationsDto locationDto : warehouseDto.getLocationsList()) {
                    Location whLocation = LOCATION.dtoToModel(locationDto, Location.class);
                    whLocation.setWarehouse(warehouse);
                    locationList.add(whLocation);
                }
                warehouse.setLocations(locationList);
            }

            return (U) warehouse;
        }

        @Override
        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            WarehouseDto warehouseDto = super.modelToDto(model, clazz);
            Warehouse warehouse = (Warehouse) model;
            //			List<LocationsDto> floor = warehouseDto.getLocationsList();

            if (warehouse.getLocations() != null) {
                List<LocationsDto> locationList = new ArrayList<>();
                for (Location location : warehouse.getLocations()) {
                    LocationsDto whLocation = LOCATION.modelToDto(location, LocationsDto.class);
                    locationList.add(whLocation);

                }
                warehouseDto.setLocationsList(locationList);
            }
            return (U) warehouseDto;
        }
    },
    ORDER {
        @Override
        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            OrderDto o = super.modelToDto(model, clazz);
            Order order = (Order) model;
            if (order.getDiscountPercentage() != null) {
                o.setDiscountedAmount(order.getTotalAmount() - order.getTotalAmount() * (order.getDiscountPercentage() / 100));
            }
            //            if (order.getClient() != null) {
            //                Client client = order.getClient();
            //                o.setClientId(client.getId());
            //                o.setClientName(client.getFirstName() + " " + client.getLastName());
            //            }

//			if(order.getOrderProducts() != null) {
//				OrderProductLocation orderProductLocationobj = new OrderProductLocation();
//				for(OrderProduct orderProduct : order.getOrderProducts()) {
//
//					for(OrderProductLocation orderProductLocation : orderProduct.getOrderProductsLocations()) {
//							orderProductLocationobj.setExpiry(orderProductLocation.getExpiry());
//					}
//
//				}
//			}

            if (order.getEmployee() != null) {
                EmployeeDto employeeDto = org.caansoft.core.helper.GenericConvertor.EMPLOYEE.modelToDto(order.getEmployee(), EmployeeDto.class);
                o.setEmployeeDto(employeeDto);
            }
//			if(order.getEmployeeCommission() != null){
//				o.getEmployeeDto().setCommissionPercentage(order.getEmployeeCommission());
//			}

            if (order.getOrderProducts() != null) {
                List<ProductDto> productDtos = new ArrayList<>();

                for (OrderProduct orderProduct : order.getOrderProducts()) {

                    ProductDto productDto = PRODUCT.modelToDto(orderProduct.getProduct(), ProductDto.class);

                    if (orderProduct.getOrderProductsLocations() != null) {
                        List<ProductLocationDto> productLocationDtoList = new ArrayList<>();
                        for (OrderProductLocation orderProductLocation : orderProduct.getOrderProductsLocations()) {
                            ProductLocationDto orderProductLocationDto = PRODUCT_LOCATION.modelToDto(orderProductLocation, ProductLocationDto.class);
                            if (orderProductLocation.getLocation() != null) {
                                Long locationId = orderProductLocation.getLocation().getId();
                                orderProductLocationDto.setLocationId(locationId);
                            }
                            productLocationDtoList.add(orderProductLocationDto);
                        }
                        productDto.setProductLocation(productLocationDtoList);
                    }
                    
                    if (orderProduct.getProductName() != null) {
                    	ProductNameDto nameDto = new ProductNameDto();
                    	nameDto.setId(orderProduct.getProductName().getId());
                    	nameDto.setName(orderProduct.getProductName().getName());
                    	nameDto.setProductId(orderProduct.getProductName().getProduct().getId());
                    	productDto.setProductNameDto(nameDto);
                    }

                    if (orderProduct.getOrderProductPhotos() != null) {
                        List<MediaDto> productPhotoDtoList = new ArrayList<>();
                        for (OrderProductPhoto orderProductPhoto : orderProduct.getOrderProductPhotos()) {
                            MediaDto mediaDto = new MediaDto();
                            NullAwareBeanUtilsBean.getInstance().copyProperties(mediaDto, orderProductPhoto.getMedia());
                            long productPhotoId = orderProductPhoto.getId();
                            mediaDto.setRelatedObjId(productPhotoId);
                            productPhotoDtoList.add(mediaDto);
                        }
                        productDto.setPhotos(productPhotoDtoList);
                    }
                    productDto.setOrderProductId(orderProduct.getId());                     
                    productDto.setQuantity(orderProduct.getQuantity());
                    productDto.setNumberOfUnits(orderProduct.getNumberOfUnits());

                    productDtos.add(productDto);
                }
                o.setProductList(productDtos);
            }


            if (order.getTotalAmount() != null) {
                BigDecimal bd = new BigDecimal(Double.toString(order.getTotalAmount()));
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                o.setTotalAmount(bd.floatValue());
            }

            if (order.getSignedContractMedia() != null) {
                MediaDto signedContractMediaDto = new MediaDto();
                NullAwareBeanUtilsBean.get().copyProperties(signedContractMediaDto, order.getSignedContractMedia());
                o.setSignedContractMediaDto(signedContractMediaDto);
            }

            if (order.getSignedInvoiceMedia() != null) {
                MediaDto signedInvoiceMediaDto = new MediaDto();
                NullAwareBeanUtilsBean.get().copyProperties(signedInvoiceMediaDto, order.getSignedInvoiceMedia());
                o.setSignedInvoiceMediaDto(signedInvoiceMediaDto);
            }

            if (order.getPurchaseOrderMedia() != null) {
                MediaDto purchaseOrderMediaDto = new MediaDto();
                NullAwareBeanUtilsBean.get().copyProperties(purchaseOrderMediaDto, order.getPurchaseOrderMedia());
                o.setPurchaseOrderMediaDto(purchaseOrderMediaDto);
            }

            if (order.getOrderPhotoList() != null) {
                OrderMediaDto mediaDto = null;
                List<OrderMediaDto> photos = new ArrayList<>();
                for (OrderPhoto orderPhoto : order.getOrderPhotoList()) {
                    mediaDto = new OrderMediaDto();
                    NullAwareBeanUtilsBean.get().copyProperties(mediaDto, orderPhoto.getOrderMedia());
                    mediaDto.setMeasurement(orderPhoto.getMeasurement());
                    mediaDto.setDelivery(orderPhoto.getDelivery());
                    photos.add(mediaDto);
                }
                o.setOrderPhotos(photos);
            }

            //			if(order.getInquiry() != null){
            //				InquiryDto inquiryDto = ModelDtoConvertor.INQUIRY.modelToDto(order.getInquiry(), InquiryDto.class);
            //				o.setInquiryDto(inquiryDto);
            //			}

            //            if(order.getPremium() != null){
            //                o.setPremium(order.getPremium() ? "Yes": "No");
            //            }
//			if(order.getSupervisor() != null){
//				Employee supervisor = order.getSupervisor();
//				o.setSupervisorId(supervisor.getId());
//				o.setSupervisorName(supervisor.getFirstName() + " " + supervisor.getName());
//			}
            //            if(order.getPremiumList() != null){
            //                List<PremiumDto> premiumList = new ArrayList<>();
            //                order.getPremiumList().stream().forEach(premium -> {
            //                    try {
            //                        premiumList.add(ModelDtoConvertor.PREMIUM.modelToDto(premium, PremiumDto.class));
            //                    }catch(Exception e){
            //                        e.printStackTrace();
            //                    }
            //                });
            //                o.setPremiumDtoList(premiumList);
            //            }
            if (order.getStatusHistoryList() != null) {
                StringBuilder comments = new StringBuilder("");
                order.getStatusHistoryList().forEach(history -> {
                    if (history.getComments() != null && !history.getComments().isEmpty()) {
                        comments.append(history.getComments());
                        comments.append("\n");
                    }
                });
                o.setComments(comments.toString());
            }

            if (order.getStatusHistoryList() != null) {
                StringBuilder comments = new StringBuilder("");
                List<StatusHistoryDto> statusHistoryList = new ArrayList<>();
                order.getStatusHistoryList().forEach(history -> {
                    if (history.getComments() != null && !history.getComments().isEmpty()) {
                        StatusHistoryDto status = new StatusHistoryDto();
                        status.setStatus(history.getStatus().name());
                        status.setTime(history.getCreatedOn().getTime());
                        status.setComments(history.getComments());
                        status.setUser(history.getVerifiedBy() != null ? history.getVerifiedBy().getFullName() : history.getCreatedBy());
                        statusHistoryList.add(status);
                    }
                });
//                    contractDto.setComments(comments.toString());
                o.setStatusHistoryDtoList(statusHistoryList);
            }
            if (order.getPartners() != null) {
                PartnerDto partnerDto = GenericConvertor.PARTNER.modelToDto(order.getPartners(), PartnerDto.class);
                o.setPartnerDto(partnerDto);
            }
            return (U) o;
        }

        @Override
        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            OrderDto orderDto = (OrderDto) dto;
            Order o = super.dtoToModel(dto, clazz);

            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            User loggedInUser = userService.findUser(username);

            Order dbOrder = null;

            if (orderDto.getId() != null) {
                Optional<Order> dbOrderOpt = orderRepo.findById(orderDto.getId());

                if (dbOrderOpt.isPresent()) {
                    dbOrder = dbOrderOpt.get();

                    NullAwareBeanUtilsBean.get().copyProperties(o, dbOrder);
                    NullAwareBeanUtilsBean.get().copyProperties(o, orderDto);

                    if (orderDto.getStatus() != null && !dbOrder.getStatus().equals(orderDto.getStatus())) {
                        List<OrderStatusHistory> statusHistoryList = dbOrder.getStatusHistoryList();
                        statusHistoryList.stream().filter(statusHistory -> statusHistory.getEndDate() == null).findFirst().
                                ifPresent(statusHistory -> {

//										statusHistory.setVerifiedBy(loggedInUser.getEmployee());
                                    statusHistory.setEndDate(Timestamp.from(Instant.now()));
                                });

                        OrderStatusHistory statusHistory = new OrderStatusHistory();
                        statusHistory.setOrder(o);
                        statusHistory.setStatus(orderDto.getStatus());
                        statusHistory.setStartDate(Timestamp.from(Instant.now()));
                        statusHistory.setComments(orderDto.getComments());
                        statusHistory.setVerifiedBy(loggedInUser.getEmployee());
                        statusHistoryList.add(statusHistory);
                        o.setStatusHistoryList(statusHistoryList);

                    }
                }
            }

            if (orderDto.getStatus() != null) {
                o.setStatus(orderDto.getStatus());
            }

//			if (orderDto.getEmployeeDto() != null) {
//				o.setEmployeeCommission(orderDto.getEmployeeDto().getCommissionPercentage());
//			}

            if (orderDto.getUndertaken() != null && orderDto.getUndertaken().equals(Boolean.TRUE)) {
                o.setUndertakenBy(loggedInUser.getEmployee());
            }
            //            if (orderDto.getClientId() != null) {
            //                Client client = new Warehouse();
            //                client.setId(orderDto.getClientId());
            //                o.setClient(client);
            //            }
            if (orderDto.getEmployeeDto() != null) {
                Employee employee = org.caansoft.core.helper.GenericConvertor.EMPLOYEE.dtoToModel(orderDto.getEmployeeDto(), Employee.class);
                o.setEmployee(employee);
            }
            if (orderDto.getProductList() != null) {

                Map<Long, OrderProduct> orderProductMap = new HashMap<>();
                if (dbOrder != null) {
                    dbOrder.getOrderProducts().forEach(orderProd -> orderProductMap.put(orderProd.getId(), orderProd));
                }
                List<OrderProduct> orderProductList = new ArrayList<>();
                for (ProductDto productDto : orderDto.getProductList()) {
                    Product product = PRODUCT.dtoToModel(productDto, Product.class);
                    if (product.getFrozen() != null && product.getFrozen().equals(Boolean.TRUE)) {
                        o.setFrozen(product.getFrozen());
                    }

                    OrderProduct orderProduct = null;

                    if (orderProductMap.containsKey(productDto.getOrderProductId())) {
                        orderProduct = orderProductMap.get(productDto.getOrderProductId());

                    } else {
                        orderProduct = new OrderProduct();
                        orderProduct.setId(productDto.getOrderProductId());
                        if(productDto.getProductNameDto() != null) {
	                        ProductName productName = new ProductName();
	                        productName.setId(
	                        		productDto.getProductNameDto() != null ? 
	                        				productDto.getProductNameDto().getId() : null );
	                        productName.setName(productDto.getProductNameDto().getName() != null 
	                        		? productDto.getProductNameDto().getName() : null);
	                        if (productDto.getProductNameDto().getProductId() != null) {
	                        	Product otherProduct = new Product();
	                        	product.setId(productDto.getProductNameDto().getProductId());
	                        	productName.setProduct(product);	                        	
	                        }	                        
	                        orderProduct.setProductName(productName);
                        }
                        orderProduct.setOrder(o);
                        orderProduct.setProduct(product);
                        orderProduct.setQuantity(productDto.getQuantity());
                        orderProduct.setNumberOfUnits(productDto.getNumberOfUnits());                        
                    }

                    if (productDto.getProductLocation() != null && !productDto.getProductLocation().isEmpty()) {
                        List<OrderProductLocation> orderProductLocationList = new ArrayList<>();

                        for (ProductLocationDto productLocationDto : productDto.getProductLocation()) {
                            OrderProductLocation orderProductLocation = PRODUCT_LOCATION.dtoToModel(productLocationDto, OrderProductLocation.class);
                            orderProductLocation.setOrderProducts(orderProduct);
                            if (productLocationDto.getLocationId() != null) {
                                Location location = new Location();
                                location.setId(productLocationDto.getLocationId());
                                orderProductLocation.setLocation(location);
                            }
                            orderProductLocationList.add(orderProductLocation);
                        }
                        orderProduct.setOrderProductsLocations(orderProductLocationList);
                    }

                    if (productDto.getPhotos() != null && !productDto.getPhotos().isEmpty()) {
                        List<OrderProductPhoto> orderProductPhotos = new ArrayList<>();
                        for (MediaDto productPhotoDto : productDto.getPhotos()) {
                            OrderProductPhoto orderProductPhoto = new OrderProductPhoto();
                            Long productPhotoId = productPhotoDto.getRelatedObjId() != 0 ? productPhotoDto.getRelatedObjId() : null;
                            orderProductPhoto.setId(productPhotoId);
                            orderProductPhoto.setOrderProduct(orderProduct);

                            Media productMedia = new Media();
                            NullAwareBeanUtilsBean.get().copyProperties(productMedia, productPhotoDto);

                            orderProductPhoto.setMedia(productMedia);
                            orderProductPhotos.add(orderProductPhoto);
                        }
                        orderProduct.setOrderProductPhotos(orderProductPhotos);
                    }

                    orderProductList.add(orderProduct);

                }
                o.setOrderProducts(orderProductList);
            }


            if (orderDto.getTotalAmount() != null) {
                BigDecimal bd = new BigDecimal(Double.toString(orderDto.getTotalAmount()));
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                o.setTotalAmount(bd.floatValue());
            }

            if (orderDto.getOrderPhotos() != null && !orderDto.getOrderPhotos().isEmpty()) {
                List<OrderPhoto> orderPhotos = new ArrayList<>();
                for (OrderMediaDto orderMediaDto : orderDto.getOrderPhotos()) {
                    Media orderMedia = new Media();
                    NullAwareBeanUtilsBean.get().copyProperties(orderMedia, orderMediaDto);
                    OrderPhoto orderPhoto = null;
                    if (orderMediaDto.getId() != null && orderMediaDto.isDelete()) {
                        orderPhoto = orderPhotoRepo.findByOrderAndOrderMedia(o, orderMedia);
                        orderPhoto.setFlag(Flag.DELETED);
                        orderPhotos.add(orderPhoto);

                    } else if (orderMediaDto.getId() == null) {
                        orderPhoto = new OrderPhoto();
                        orderPhoto.setOrder(o);
                        orderPhoto.setOrderMedia(orderMedia);
                        orderPhoto.setDelivery(orderMediaDto.getDelivery());
                        orderPhoto.setMeasurement(orderMediaDto.getMeasurement());
                        orderPhotos.add(orderPhoto);
                    }
                }
                o.setOrderPhotoList(orderPhotos);
            }

            if (orderDto.getSignedContractMediaDto() != null) {
                Media signedContractMedia = new Media();
                NullAwareBeanUtilsBean.get().copyProperties(signedContractMedia, orderDto.getSignedContractMediaDto());
                signedContractMedia.setId(orderDto.getSignedContractMediaDto().getId());
                o.setSignedContractMedia(signedContractMedia);
            }
            if (orderDto.getSignedInvoiceMediaDto() != null) {
                Media signedInvoiceMedia = new Media();
                NullAwareBeanUtilsBean.get().copyProperties(signedInvoiceMedia, orderDto.getSignedInvoiceMediaDto());
                signedInvoiceMedia.setId(orderDto.getSignedInvoiceMediaDto().getId());
                o.setSignedInvoiceMedia(signedInvoiceMedia);
            }

            if (orderDto.getPurchaseOrderMediaDto() != null) {
                Media purchaseOrderMedia = new Media();
                NullAwareBeanUtilsBean.get().copyProperties(purchaseOrderMedia, orderDto.getPurchaseOrderMediaDto());
                purchaseOrderMedia.setId(orderDto.getPurchaseOrderMediaDto().getId());
                o.setPurchaseOrderMedia(purchaseOrderMedia);
            }

            //			if (orderDto.getInquiryDto() != null){
            //				Inquiry inquiry = ModelDtoConvertor.INQUIRY.dtoToModel(orderDto.getInquiryDto(), Inquiry.class);
            //				o.setInquiry(inquiry);
            //				inquiry.setOrder(o);
            //			}
//			if(orderDto.getSupervisorId() != null){
//				Employee supervisor = new Employee();
//				supervisor.setId(orderDto.getSupervisorId());
//				o.setSupervisor(supervisor);
//			}
            //            if(orderDto.getPremiumDtoList() != null){
            //                List<OrderPremium> premiumList = new ArrayList<>();
            //                orderDto.getPremiumDtoList().stream().forEach(premiumDto -> {
            //                    try {
            //                        OrderPremium premium = ModelDtoConvertor.PREMIUM.dtoToModel(premiumDto, OrderPremium.class);
            //                        premium.setOrder(o);
            //                        premiumList.add(premium);
            //                    }catch(Exception e){
            //                        e.printStackTrace();
            //                    }
            //                });
            //                o.setPremiumList(premiumList);
            //            }
            if (orderDto.getPartnerDto() != null) {
                Partner partner = new Partner();
                partner.setId(orderDto.getPartnerDto().getId());
                o.setPartners(partner);
            }
            return (U) o;
        }
    },
    PRODUCT_LOCATION {
        @Override
        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            ProductLocationDto productLocationDto = super.modelToDto(model, clazz);
            return (U) productLocationDto;
        }

        @Override
        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            OrderProductLocation orderProductLocation = super.dtoToModel(dto, clazz);
            return (U) orderProductLocation;

        }
    },

    PRODUCT {
        @Override
        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            ProductDto o = super.modelToDto(model, clazz);
            Product product = (Product) model;

            if (product.getCategory() != null) {
                CategoryDto categoryDto = CATEGORY.modelToDto(product.getCategory(), CategoryDto.class);
                o.setCategoryDto(categoryDto);
            }
            if (product.getProductName() != null && !product.getProductName().isEmpty()) {
            	List<ProductNameDto> productNameDtoList = new ArrayList<>();
            	for(ProductName name : product.getProductName()) {
            		ProductNameDto dto = new ProductNameDto();
            		dto.setName(name.getName());            		            	
            		dto.setProductId(name.getProduct().getId());
            		dto.setId(name.getId());
            		productNameDtoList.add(dto);
            	}
            	o.setProductNameDtoList(productNameDtoList != null && !productNameDtoList.isEmpty() ? productNameDtoList : null);            	 
            }
            return (U) o;
        }

        @Override
        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            Product product = super.dtoToModel(dto, clazz);
            ProductDto productDto = (ProductDto) dto;
            if (productDto.getOrder() != null) {
                List<OrderProduct> orderProducts = new ArrayList<>();
                OrderProduct orderProduct = new OrderProduct();
                Order mOrder = new Order();
                ProductName productName = new ProductName();
                for (OrderDto order : productDto.getOrder()) {
                    orderProduct.setProduct(product);
                    mOrder.setId(order.getId());                                       
                    productName.setId(productDto.getProductNameDto() != null ? productDto.getProductNameDto().getId() : null);
                    orderProduct.setProductName(productName);
                    orderProduct.setOrder(mOrder);
                    orderProduct.setProductName(productName);
                    orderProduct.setNumberOfUnits(productDto.getNumberOfUnits());
                    orderProducts.add(orderProduct);
                }
                product.setOrderProducts(orderProducts);
            }
            if (productDto.getCategoryDto() != null && productDto.getCategoryDto().getId() != null) {
                Category category = new Category();
                category.setId(productDto.getCategoryDto().getId());
                product.setCategory(category);
            }
            return (U) product;
        }
    },
    CATEGORY {
        @Override
        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            Category o = super.dtoToModel(dto, clazz);
            return (U) o;
        }

        @Override
        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
            CategoryDto o = super.modelToDto(model, clazz);
            return (U) o;

        }
    }, PREMIUM;

    private static UserService userService;

    private static OrderRepository orderRepo;
    private static StockCountRepository stockCountRepository;
    private static OrderPhotoRepository orderPhotoRepo;

    public static void setUserService(UserService userService) {
        ModelDtoConvertor.userService = userService;
    }

    public static void setOrderRepo(OrderRepository orderRepo) {
        ModelDtoConvertor.orderRepo = orderRepo;
    }

    public static void setStockCountRepository(StockCountRepository stockCountRepository) {
        ModelDtoConvertor.stockCountRepository = stockCountRepository;
    }

    public static void setOrderPhotoRepo(OrderPhotoRepository orderPhotoRepo) {
        ModelDtoConvertor.orderPhotoRepo = orderPhotoRepo;
    }

    private static OrderProductRepository orderProductRepository;
}
