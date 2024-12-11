package org.caansoft.sdfood.serviceimpl;

import org.caansoft.core.common.NullAwareBeanUtilsBean;
import org.caansoft.core.common.exception.CoreRuntimeException;
import org.caansoft.core.dto.BaseDto;
import org.caansoft.core.dto.MediaDto;
import org.caansoft.core.enums.AccessLevelEnum;
import org.caansoft.core.enums.Flag;
import org.caansoft.core.model.Agency;
import org.caansoft.core.model.Employee;
import org.caansoft.core.model.Media;
import org.caansoft.core.model.Team;
import org.caansoft.core.model.User;
import org.caansoft.core.repo.AgencyRepository;
import org.caansoft.core.repo.TeamRepository;
import org.caansoft.core.service.UserService;
import org.caansoft.sdfood.dto.InventoryOrderedDto;
import org.caansoft.sdfood.dto.LocationsDto;
import org.caansoft.sdfood.dto.OrderDto;
import org.caansoft.sdfood.dto.ProductDto;
import org.caansoft.sdfood.dto.ProductLocationDto;
import org.caansoft.sdfood.dto.StatsDto;
import org.caansoft.sdfood.dto.SubstitueOrderProductDTO;
import org.caansoft.sdfood.enums.ModelDtoConvertor;
import org.caansoft.sdfood.enums.OrderStatus;
import org.caansoft.sdfood.enums.ProductStockTransactionType;
import org.caansoft.sdfood.model.*;
import org.caansoft.sdfood.prestashop.service.PrestashopOrdersService;
import org.caansoft.sdfood.prestashopIntegration.PrestashopService;
import org.caansoft.sdfood.repo.OrderProductLocationRepo;
import org.caansoft.sdfood.repo.OrderProductRepository;
import org.caansoft.sdfood.repo.OrderRepository;
import org.caansoft.sdfood.repo.PrestashopOrderProductsRepository;
import org.caansoft.sdfood.repo.ProductNameRepository;
import org.caansoft.sdfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.caansoft.sdfood.model.ProductStock;
import org.caansoft.sdfood.repo.ProductStockRepository;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
	
    @Autowired
    private PrestashopService prestashopService;
    @Autowired
    private UserService userService;

    @Value("${akadia.order.threshold.amount}")
    private Long thresholdAmount;


    @Value("${akadia.product.expiry.threshold.days:10}")
    private Long productExpiryDaysThreshold;

    @Autowired
    private AgencyRepository agencyRepo;
    @Autowired
    private TeamRepository teamRepo;
    
    @Autowired
    PrestashopOrdersService prestashopOrderService;
    
    @Autowired
    ProductStockRepository productStockRepo;

    private final OrderRepository orderRepository;
    
    @Autowired
    private PrestashopOrderProductsRepository prestashopOrderProductRepo;
    
    @Autowired
    private OrderProductRepository orderProductRepo;
    
    @Autowired
    private OrderProductLocationRepo oplRepo;

//    @Autowired
//    private ProductServiceRepository productServiceRepository;

    @Autowired
    private OrderNumberService orderNumberService;
    
    @Autowired
    private ProductNameRepository productNameRepo;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public <T extends BaseDto> T add(T dto) {
        OrderDto orderDto = (OrderDto) dto;
        try {
//            GenericConverter.setProductServiceRepository(productServiceRepository);
        	
        	orderDto = mergeDuplicateProductsInOrder(orderDto);
        	
        	orderDto = saveOtherProductNames(orderDto);       	        
        	
            ModelDtoConvertor.setUserService(userService);
            ModelDtoConvertor.setOrderRepo(orderRepository);
            Order order = ModelDtoConvertor.ORDER.dtoToModel(orderDto, Order.class);
            order.setNumber(generateOrderNumber());
            order.setQuotationDate(Timestamp.valueOf(LocalDateTime.now()));
            order.setFlag(Flag.ACTIVE);
            order.setStatus(OrderStatus.NEW);


            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            if (username.isEmpty() || username.equals("anonymousUser")) {
                throw new CoreRuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED);
            }
            OrderStatusHistory statusHistory = new OrderStatusHistory();
            statusHistory.setOrder(order);
            statusHistory.setStatus(order.getStatus());
            statusHistory.setStartDate(Timestamp.from(Instant.now()));
            order.setStatusHistoryList(Arrays.asList(statusHistory));

            order = orderRepository.save(order);
            orderDto = ModelDtoConvertor.ORDER.modelToDto(order, OrderDto.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return (T) orderDto;
    }
    
    @Override
    public <T extends BaseDto> Page<T> find(Pageable pageable) {
        return (Page<T>) new PageImpl<OrderDto>(Collections.EMPTY_LIST, pageable, 0);
    }

    @Override
    public <T extends BaseDto> Page<T> find(Order order, Date fromDate, Date toDate, OrderStatus status, boolean project, String productName, Date startDeliveryDate, Date endDeliveryDate, Pageable pageable) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.isEmpty() || username.equals("anonymousUser")) {
            throw new CoreRuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED);
        }
        User loggedInUser = userService.findUser(username);

        if (loggedInUser.getEmployee() == null || loggedInUser.getEmployee().getJob() == null) {
            throw new CoreRuntimeException(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN);
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Order> example = Example.of(order, matcher);

        Page<Order> orders = null;

        if (loggedInUser.getEmployee().getJob().getAccessLevel().getLevel().equals(AccessLevelEnum.All)) {
            orders = orderRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, null, Example.of(order, matcher),status, productName, startDeliveryDate, endDeliveryDate), pageable);
        } else if (loggedInUser.getEmployee().getJob().getAccessLevel().getLevel().equals(AccessLevelEnum.Agency)) {
            List<Team> teams = new ArrayList<>();
            List<Agency> agencies = agencyRepo.findByAgencyDirector(loggedInUser.getEmployee());
            agencies.stream().forEach(agency -> agency.getClans().stream().forEach(clan -> teams.addAll(clan.getTeams())));
            List<Employee> employees = new ArrayList<>();
            teams.stream().forEach(team -> team.getTeamEmployees().stream().forEach(teamEmp -> employees.add(teamEmp.getEmployee())));
            orders = orderRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, employees, Example.of(order, matcher), status, productName, startDeliveryDate, endDeliveryDate), pageable);
        } else if (loggedInUser.getEmployee().getJob().getAccessLevel().getLevel().equals(AccessLevelEnum.Team)) {
            List<Team> teams = teamRepo.findByTeamChief(loggedInUser.getEmployee());
            List<Employee> employees = new ArrayList<>();
            teams.stream().forEach(team -> team.getTeamEmployees().stream().forEach(teamEmp -> employees.add(teamEmp.getEmployee())));
            orders = orderRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, employees, Example.of(order, matcher), status, productName, startDeliveryDate,endDeliveryDate), pageable);
        } else {
            orders = orderRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, Arrays.asList(loggedInUser.getEmployee()), Example.of(order, matcher), status, productName, startDeliveryDate,endDeliveryDate), pageable);
        }

        List<OrderDto> orderDtoList = new ArrayList<>();
        for (Order dbOrder : orders) {
            try {
                orderDtoList.add(ModelDtoConvertor.ORDER.modelToDto(dbOrder, OrderDto.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return (Page<T>) new PageImpl<OrderDto>(orderDtoList, pageable, orders.getTotalElements());
    }

    @Override
    public <T extends BaseDto> T findOne(long id) {
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isPresent()) {
            try {
                return ModelDtoConvertor.ORDER.modelToDto(optional.get(), OrderDto.class);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    @Override
    public void delete(long id) {
        Order order = orderRepository.findById(id).get();
        order.setFlag(Flag.DELETED);
        orderRepository.save(order);

    }

    @Override
    public <T extends BaseDto> T update(T dto) {
        try {
            ModelDtoConvertor.setUserService(userService);
            ModelDtoConvertor.setOrderRepo(orderRepository);
            OrderDto orderDto = (OrderDto) dto;
//           GenericConverter.setProductServiceRepository(productServiceRepository);     
            
            orderDto = mergeDuplicateProductsInOrder(orderDto);
            
            orderDto = saveOtherProductNames(orderDto);
            
            Order order = ModelDtoConvertor.ORDER.dtoToModel(orderDto, Order.class);
            if (orderDto.getAction() != null && orderDto.getAction().equals("PUBLISH")){
                prestashopService.updateStock(order.getOrderProducts());
                order.setStatus(OrderStatus.PUBLISHED);
            }            

//             Optional<Order> dbOrderOptional = orderRepository.findById(order.getId());
//             if (dbOrderOptional.isPresent()) {
//                 Order dbOrder = dbOrderOptional.get();
//                 dbOrder.getOrderProducts().clear();
//                 List<org.caansoft.sdfood.model.OrderProduct> orderProducts = order.getOrderProducts();
//                 order.setOrderProducts(null);
//                 NullAwareBeanUtilsBean.get().copyProperties(dbOrder, order);
//                 dbOrder.getOrderProducts().addAll(orderProducts);
//                 order = orderRepository.save(dbOrder);
//             }
            order = orderRepository.save(order);
            
            if (orderDto.getStatus() != null && orderDto.getStatus().equals(OrderStatus.STOCKED) && orderDto.getProductList() != null && !orderDto.getProductList().isEmpty() && 
            		(orderDto.getAction() != null && orderDto.getAction().equals("STOCKED"))) {
            	List<ProductStock> psList = new ArrayList<>();  
            	for (ProductDto productDto : orderDto.getProductList()) {
            		Product product = new Product();
    				product.setId(productDto.getId());        				
    				
            		for (ProductLocationDto oplDto : productDto.getProductLocation()) {
            			ProductStock ps = new ProductStock();                		        				
        				ps.setLotNumber(oplDto.getLotNumber() != null ? oplDto.getLotNumber() : null);
        				ps.setQuantityIn(oplDto.getReceivedNumberofUnits() != null ? oplDto.getReceivedNumberofUnits().intValue() :
        					oplDto.getReceivedQuantity().intValue());
        				ps.setExprityDate(oplDto.getExpiry() != null ? oplDto.getExpiry() : null);
        				ps.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        				ps.setProduct(product);
        				ps.setTransactionType(ProductStockTransactionType.INVENTORY);
        				ps.setPrestashopProductId(productDto.getPrestashopProductId() != null ? productDto.getPrestashopProductId().longValue() : null);
        				ps.setOrderId(order.getId());
        				psList.add(ps);        				
        				if (oplDto.getDamagedQuantity() != null || oplDto.getDamageNumberOfUnits() != null) {
        					ProductStock psDamaged = new ProductStock();//        					
        					psDamaged.setLotNumber(oplDto.getLotNumber());
        					psDamaged.setQuantityIn(oplDto.getDamageNumberOfUnits() != null 
        							? oplDto.getDamageNumberOfUnits() : oplDto.getDamageNumberOfUnits());
        					psDamaged.setExprityDate(oplDto.getExpiry() != null ? oplDto.getExpiry() : null);
        					ps.setCreatedOn(new Timestamp(System.currentTimeMillis()));
            				Product productDamaged = new Product();
            				productDamaged.setId(productDto.getId());        				
            				psDamaged.setProduct(product);
            				psDamaged.setCreatedOn(new Timestamp(System.currentTimeMillis()));
            				psDamaged.setTransactionType(ProductStockTransactionType.DAMAGED_INVENTORY);
            				psDamaged.setPrestashopProductId(productDto.getPrestashopProductId() != null ? productDto.getPrestashopProductId().longValue() : null);
            				psDamaged.setOrderId(order.getId());
            				psList.add(psDamaged);
        				}        			        				        				        			        				
            		}            		
            	}  
            	productStockRepo.saveAll(psList);
            	
            }
            
            dto = ModelDtoConvertor.ORDER.modelToDto(order, OrderDto.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dto;
    }
    
    private OrderDto mergeDuplicateProductsInOrder(OrderDto orderDto) {
    
    	Map<Long, ProductDto> productDtoMap = new HashMap<>();
    	for(ProductDto productDto: orderDto.getProductList()) {
    		if(productDtoMap.containsKey(productDto.getId())) {
    			ProductDto pDto = productDtoMap.get(productDto.getId());
    			Float pallets = pDto.getQuantity() + productDto.getQuantity();
    			Integer numberOfUnits = pDto.getNumberOfUnits() + productDto.getNumberOfUnits();
    			productDto.setQuantity(pallets);
    			productDto.setNumberOfUnits(numberOfUnits);
    			productDtoMap.put(productDto.getId(), productDto);
    		}else {
    			productDtoMap.put(productDto.getId(), productDto);
    		}
    	}
    	orderDto.setProductList(new ArrayList<>(productDtoMap.values()));
    	return orderDto;
    }
    
    public OrderDto saveOtherProductNames(OrderDto dto) {
    	
    	OrderDto orderDto = dto;
    	try {
    		    		
    		List<ProductName> productNameList = new ArrayList<>();
        	for(ProductDto productDto : orderDto.getProductList()) {
        		if(productDto.getProductNameDto() != null && productDto.getProductNameDto().getId() == null) {
        			ProductName productName = new ProductName();
            		productName.setName(productDto.getProductNameDto().getName());
            		Product product = new Product();
            		product.setId(productDto.getId());
            		productName.setProduct(product);
            		productNameList.add(productName);
        		}        		       	
        	}
        	
        	if(productNameList != null && !productNameList.isEmpty()) {
        		
        		List<ProductName> newProductNameList = productNameRepo.saveAll(productNameList);
        		
        		Map<Long, ProductDto> productDtoMap = orderDto.getProductList().stream()
                		.collect(Collectors.toMap(ProductDto :: getId, Function.identity()));
           
        		for(ProductName productName :  newProductNameList) {
            		if(productDtoMap.containsKey(productName.getProduct().getId())) {
            			ProductDto productDto = productDtoMap.get(productName.getProduct().getId()); 
            			
            			productDto.getProductNameDto().setId(productName.getId());
            			productDtoMap.put(productName.getProduct().getId(), productDto);
            		}
            	}
            	
            	orderDto.setProductList(new ArrayList<>(productDtoMap.values()));
            	return orderDto;
        	}  
    	} catch(Exception e) {
    		throw new RuntimeException(e);
    	}    	
    	
    	return orderDto;
    }

    @Override
    public StatsDto dashboard(Order order, Date fromDate, Date toDate, Pageable pageable) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.isEmpty() || username.equals("anonymousUser")) {
            throw new CoreRuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED);
        }
        User loggedInUser = userService.findUser(username);

        if (loggedInUser.getEmployee() == null || loggedInUser.getEmployee().getJob() == null) {
            throw new CoreRuntimeException(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN);
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();



        Example<Order> example = Example.of(order, matcher);

        Page<Order> orders = orderRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, null, Example.of(order, matcher),null, null,null, null
        ), pageable);        
        ;
                
        StatsDto statsDto = prestashopOrderService.findTotalOrdersAndAmount(fromDate, toDate);
        
        
//        if (loggedInUser.getEmployee().getJob().getAccessLevel().getLevel().equals(AccessLevelEnum.All)) {
//            orders = orderRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, null, Example.of(order, matcher), null), pageable);
//        } else if (loggedInUser.getEmployee().getJob().getAccessLevel().getLevel().equals(AccessLevelEnum.Agency)) {
//            List<Team> teams = new ArrayList<>();
//            List<Agency> agencies = agencyRepo.findByAgencyDirector(loggedInUser.getEmployee());
//            agencies.stream().forEach(agency -> agency.getClans().stream().forEach(clan -> teams.addAll(clan.getTeams())));
//            List<Employee> employees = new ArrayList<>();
//            teams.stream().forEach(team -> team.getTeamEmployees().stream().forEach(teamEmp -> employees.add(teamEmp.getEmployee())));
//            orders = orderRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, employees, Example.of(order, matcher), null), pageable);
//        } else if (loggedInUser.getEmployee().getJob().getAccessLevel().getLevel().equals(AccessLevelEnum.Team)) {
//            List<Team> teams = teamRepo.findByTeamChief(loggedInUser.getEmployee());
//            List<Employee> employees = new ArrayList<>();
//            teams.stream().forEach(team -> team.getTeamEmployees().stream().forEach(teamEmp -> employees.add(teamEmp.getEmployee())));
//            orders = orderRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, employees, Example.of(order, matcher), null), pageable);
//        } else {
//            orders = orderRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, Arrays.asList(loggedInUser.getEmployee()), Example.of(order, matcher), status), pageable);
//        }

        int numberOfNewOrders = 0;
        int numberOfValidatedOrders = 0;
        int numberOfReceivedOrders = 0;
        int numberOfInTransitOrders = 0;
        int numberOfStockedOrders = 0;
        int numberOfCancelledOrders = 0;
        Map<Product, Map<Location, Float>> prodLocatioDamagedMap = new HashMap<>();
        Map<Location, Float> locationDamangedCountMap = null;
        Map<Integer, Map<Product, Map<Location, Float>>> expGroupMap = new HashMap<>();
        Map<Product, Map<Location, Float>> prodLocatioExpMap = new HashMap<>();
        Map<Location, Float> locationExpCountMap = null;
        float damagedQuantity = 0;
        List<OrderProduct> orderProducts = new ArrayList<>();
        List<OrderProductLocation> orderProductLocations = new ArrayList<>();

        for (Order o : orders) {
            if (o.getStatus().equals(OrderStatus.NEW)) {
                numberOfNewOrders = numberOfNewOrders + 1;
            } else if (o.getStatus().equals(OrderStatus.VALIDATED)) {
                numberOfValidatedOrders = numberOfValidatedOrders + 1;
            } else if (o.getStatus().equals(OrderStatus.CONFIRMED)) {
                numberOfInTransitOrders = numberOfInTransitOrders + 1;
            } else if (o.getStatus().equals(OrderStatus.RECEIVED)) {
                numberOfReceivedOrders = numberOfReceivedOrders + 1;
            } else if (o.getStatus().equals(OrderStatus.STOCKED)) {
                numberOfStockedOrders = numberOfStockedOrders + 1;
            } else if (o.getStatus().equals(OrderStatus.CANCELLED)) {
                numberOfCancelledOrders = numberOfCancelledOrders + 1;
            }
            if (o.getStatus().equals(OrderStatus.RECEIVED) || o.getStatus().equals(OrderStatus.STOCKED)) {
                orderProducts.addAll(o.getOrderProducts());
            }
            if (o.getStatus().equals(OrderStatus.PUBLISHED)) {
                orderProducts.addAll(o.getOrderProducts());
            }
        }

        for (OrderProduct orderProduct : orderProducts) {
            orderProductLocations.addAll(orderProduct.getOrderProductsLocations());
        }


        for (OrderProductLocation loc : orderProductLocations) {
            if (loc.getDamagedQuantity() != null && loc.getDamagedQuantity() > 0) {
                if (!prodLocatioDamagedMap.containsKey(loc.getOrderProducts().getProduct())) {
                    prodLocatioDamagedMap.put(loc.getOrderProducts().getProduct(), new HashMap<>());
                }
                locationDamangedCountMap = prodLocatioDamagedMap.get(loc.getOrderProducts().getProduct());

                if (!locationDamangedCountMap.containsKey(loc.getLocation())) {
                    locationDamangedCountMap.put(loc.getLocation(), 0f);
                }
                //damagedQuantity = damagedQuantity + loc.getDamagedQuantity();
                locationDamangedCountMap.put(loc.getLocation(), locationDamangedCountMap.get(loc.getLocation()) + loc.getDamagedQuantity());
            }

            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE, productExpiryDaysThreshold.intValue());
            long expThresholdMillis = instance.getTime().getTime();
            long currMillis = System.currentTimeMillis();
            if(loc.getExpiry() == null) {
            	continue;
            }
            long expMillis = loc.getExpiry().getTime();
            if (expThresholdMillis > expMillis) {
                int days = (int) Math.ceil((expMillis - currMillis)/1000/60/60/24);
                if (!expGroupMap.containsKey(days)) {
                    expGroupMap.put(days, new HashMap<>());
                }

                prodLocatioExpMap = expGroupMap.get(days);
                if (!prodLocatioExpMap.containsKey(loc.getOrderProducts().getProduct())) {
                    prodLocatioExpMap.put(loc.getOrderProducts().getProduct(), new HashMap<>());
                }

                locationExpCountMap = prodLocatioExpMap.get(loc.getOrderProducts().getProduct());
                if (!locationExpCountMap.containsKey(loc.getLocation())) {
                    locationExpCountMap.put(loc.getLocation(), 0f);
                }
                //damagedQuantity = damagedQuantity + loc.getDamagedQuantity();
                if(loc.getReceivedQuantity() != null) {
                    locationExpCountMap.put(loc.getLocation(), locationExpCountMap.get(loc.getLocation()) + loc.getReceivedQuantity());
                }
                else if(loc.getReceivedNumberofUnits() != null) {
                    locationExpCountMap.put(loc.getLocation(), locationExpCountMap.get(loc.getLocation()) + loc.getReceivedNumberofUnits());
                }
            }
        }


        List<StatsDto.DamagedExpiredLocationWise> damangedProdLocCounts = new ArrayList<>();

        for (Product product : prodLocatioDamagedMap.keySet()) {
            locationDamangedCountMap = prodLocatioDamagedMap.get(product);
            for (Location location : locationDamangedCountMap.keySet()) {
                try {
                    StatsDto.DamagedExpiredLocationWise damagedExpiredLocationWise = new StatsDto.DamagedExpiredLocationWise(locationDamangedCountMap.get(location),
                            ModelDtoConvertor.PRODUCT.modelToDto(product, ProductDto.class),
                            ModelDtoConvertor.LOCATION.modelToDto(location, LocationsDto.class));
                    damangedProdLocCounts.add(damagedExpiredLocationWise);
                } catch (Exception e) {

                }
            }
        }

        List<StatsDto.DamagedExpiredLocationWise> expiredProdLocCounts = new ArrayList<>();

        for (Integer days : expGroupMap.keySet()) {
            prodLocatioExpMap = expGroupMap.get(days);
            for (Product product : prodLocatioExpMap.keySet()) {
                locationExpCountMap = prodLocatioExpMap.get(product);
                for (Location location : locationExpCountMap.keySet()) {
                    try {
                        StatsDto.DamagedExpiredLocationWise damagedExpiredLocationWise = new StatsDto.DamagedExpiredLocationWise(locationExpCountMap.get(location),
                                ModelDtoConvertor.PRODUCT.modelToDto(product, ProductDto.class),
                                ModelDtoConvertor.LOCATION.modelToDto(location, LocationsDto.class), days);
                        expiredProdLocCounts.add(damagedExpiredLocationWise);
                    } catch (Exception e) {

                    }
                }
            }
        }

        StatsDto stats = new StatsDto();
        stats.setNewOrders(numberOfNewOrders);
        stats.setValidatedOrders(numberOfValidatedOrders);
        stats.setInTransitOrders(numberOfInTransitOrders);
        stats.setStockedOrders(numberOfStockedOrders);
        stats.setCancelledOrders(numberOfCancelledOrders);
        stats.setReceivedOrders(numberOfReceivedOrders);
        stats.setDamagedQuantity(damagedQuantity);
        stats.setDamagedLocationWiseCounts(damangedProdLocCounts);        
        stats.setTotalOrders(statsDto.getTotalSalesQuantity());
        stats.setTotalSaleAmount(statsDto.getTotalSaleAmount());        
        Collections.sort(expiredProdLocCounts, new Comparator<StatsDto.DamagedExpiredLocationWise>() {
            @Override
            public int compare(StatsDto.DamagedExpiredLocationWise o1, StatsDto.DamagedExpiredLocationWise o2) {
                if (o1.getDaysToExpire() < o2.getDaysToExpire())
                    return -1;
                else if (o1.getDaysToExpire() > o2.getDaysToExpire())
                    return 1;
                else
                    return 0;
            }
        });
        stats.setExpiredLocationWiseCounts(expiredProdLocCounts);
        stats.setExpiryDaysThreshold(productExpiryDaysThreshold.intValue());
        return stats;
    }

    private String generateOrderNumber() {
//        OptionalInt number = new Random().ints(1, 6).findFirst();
//        return "AK-" + number.getAsInt();
        OrderNumber orderNumber = orderNumberService.generateQuotationNumber();
        LocalDate date = LocalDate.now();
        return "SD" + date.getYear() + date.getMonthValue() + "-" + orderNumber.getQuotationNumber();

    }

    private String generateInvoiceNumber() {
        OrderNumber orderNumber = orderNumberService.generateInvoiceNumber();
        LocalDate date = LocalDate.now();
        return "SD" + date.getYear() + date.getMonthValue() + "-" + orderNumber.getInvoiceNumber();

    }


    private Specification<Order> getSpecFromDatesAndExample(Date fromDate, Date toDate, List<Employee> employees, Example<Order> example, OrderStatus status, String productName, Date startDeliveryDate, Date endDeliveryDate) {

        return (Specification<Order>) (root, query, builder) -> {

            final List<Predicate> predicates = new ArrayList<>();

            if (fromDate != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdOn"), fromDate));
            }
            if (toDate != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createdOn"), toDate));
            }
            if (startDeliveryDate != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("deliveryDate"), startDeliveryDate));
            }
            if (endDeliveryDate != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("deliveryDate"), endDeliveryDate));
            }


//            if (employees != null && !employees.isEmpty()) {
//                predicates.add(root.get(Order_.EMPLOYEE).in(employees));			//todo
//            }
            if (productName != null) {
                Join<Order, OrderProduct> orderProductJoin = root.join("orderProducts");
                Join<OrderProduct, Product> productJoin = orderProductJoin.join("product");
                predicates.add(builder.like(productJoin.get("name"), "%" + productName + "%"));
            }
            if (status != null) {
                predicates.add(builder.equal(root.get("status"), status));
            } else {
                predicates.add(root.get("status").in(Arrays.asList(OrderStatus.values())));
            }
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
    
    public List<SubstitueOrderProductDTO> addSubstituteOrderProduct(List<SubstitueOrderProductDTO> dtoList) {
    	    	
		for (SubstitueOrderProductDTO dto : dtoList) {

			OrderProduct orderProduct = orderProductRepo.findByOrderIdAndProductId(dto.getOrderId(),
					dto.getProductId());									
			if (orderProduct == null) {
				orderProduct = new OrderProduct();
				Order order = new Order();
				order.setId(dto.getOrderId());
				Product product = new Product();
				product.setId(dto.getProductId());
				orderProduct.setOrder(order);
				orderProduct.setProduct(product);
				orderProduct.setQuantity(new Float(-1));								
			}
			
			if (dto.getPhotos() != null && !dto.getPhotos().isEmpty()) {
				List<OrderProductPhoto> oppList = new ArrayList<>();
				for(MediaDto mediaDTO : dto.getPhotos()) {
					OrderProductPhoto orderProductPhoto = new OrderProductPhoto();
					Long productPhotoId = mediaDTO.getRelatedObjId() != 0 ? mediaDTO.getRelatedObjId() : null;
					orderProductPhoto.setId(productPhotoId);
	                orderProductPhoto.setOrderProduct(orderProduct);
	                Media productMedia = new Media();
	                try {
						NullAwareBeanUtilsBean.get().copyProperties(productMedia, mediaDTO);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                orderProductPhoto.setMedia(productMedia);
	                oppList.add(orderProductPhoto);
				}                      
				orderProduct.setOrderProductPhotos(oppList);
			}			
			orderProductRepo.save(orderProduct);
			
			OrderProductLocation opl = new OrderProductLocation();
			opl.setDamagedQuantity(dto.getDamagedQuantity() != null ? dto.getDamagedQuantity() : null);
			opl.setDamageNumberOfUnits(dto.getDamagedNumberOfUnits() != null ? dto.getDamagedNumberOfUnits() : null);
			opl.setExpiry(dto.getExpiry() != null ? new Timestamp(dto.getExpiry()) : null);
			if(dto.getLocationId() != null) {
				Location location = new Location();
				location.setId(dto.getLocationId());
				opl.setLocation(location);
			} else {
				opl.setLocation(null);
			}			
			opl.setLotNumber(dto.getLotNumber() != null ? dto.getLotNumber() : null);
			opl.setOrderProducts(orderProduct);
			opl.setReceivedNumberofUnits(dto.getReceivedNumberOfUnits() != null ? dto.getReceivedNumberOfUnits() : null);
			opl.setReceivedQuantity(dto.getReceivedQuantity() != null ? dto.getReceivedQuantity().floatValue() : null);
			opl.setReceivedPallets(dto.getReceivedPallets() != null ? dto.getReceivedPallets() : null);
			oplRepo.save(opl);
		}
		return dtoList;
	}

	@Override
	public Page<InventoryOrderedDto> getInventoryOrdered(Long startDate, Long endDate, String productName, String vendorName, 
			Boolean isPageable, Pageable pageable) {			
		
		Timestamp sDate = startDate != null ? new Timestamp(startDate) : null;
		Timestamp eDate = endDate != null ? new Timestamp(endDate) : null;
		
		Page<OrderProductLocation> orderedInventoryList = oplRepo.findByOrderProductLocation(sDate, eDate, productName, vendorName, !isPageable ? Pageable.unpaged() : pageable);
		if (orderedInventoryList != null && !orderedInventoryList.isEmpty()) {
						
			List<InventoryOrderedDto> dtoList = new ArrayList<InventoryOrderedDto>();
			for (OrderProductLocation opl : orderedInventoryList) {
				InventoryOrderedDto dto = new InventoryOrderedDto();
				dto.setId(opl.getQueryProductId() + opl.getVendorId());
				dto.setName(opl.getProductName());
				dto.setVendorName(opl.getVendorName());
				dto.setTotalOrderedPallets(opl.getTotalOrderedPallets());
				dto.setTotalReceivedPallets(opl.getTotalReceivedPallets());
				dto.setTotalOrderedNumberOfUnits(opl.getTotalOrderedNumberOfunits());
				dto.setTotalReceivedNumberOfUnits(opl.getTotalReceivedNumberOfUnits());
				dto.setDamagedQuantity(opl.getTotalDamagedQuantity());					
				dtoList.add(dto);
			}
			Page<InventoryOrderedDto> pagedList = new PageImpl<InventoryOrderedDto>(dtoList, pageable, orderedInventoryList.getTotalElements());
			return pagedList;
		}
		System.out.println("Ordered Inventory List is null");
		return null;
	}

}
