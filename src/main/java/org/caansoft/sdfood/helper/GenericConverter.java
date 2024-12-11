package org.caansoft.sdfood.helper;

import org.caansoft.sdfood.dto.*;
import org.caansoft.sdfood.enums.ModelDtoConvertor;
import org.caansoft.sdfood.model.*;
//import org.caansoft.sdfood.repository.AppointmentRepository;
//import org.caansoft.sdfood.repo.ProductServiceRepository;
import org.caansoft.core.common.NullAwareBeanUtilsBean;
import org.caansoft.core.dto.EmployeeDto;
import org.caansoft.core.dto.PartnerDto;
import org.caansoft.core.helper.Converter;
import org.caansoft.core.helper.GenericConvertor;
import org.caansoft.core.model.Employee;
import org.caansoft.core.model.Partner;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.caansoft.core.helper.GenericConvertor.EMPLOYEE;

public enum GenericConverter implements Converter {

	//    APPOINTMENT {
	//        @Override
	//        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
	//            AppointmentDto o = super.modelToDto(model, clazz);
	//            Appointment appointment = (Appointment) model;
	//            if (appointment.getClient() != null) {
	//                ClientDto clientDto = ModelDtoConvertor.CLIENT.modelToDto(appointment.getClient(), ClientDto.class);
	//                o.setClientDto(clientDto);
	//            }
	//            if (appointment.getEmployee() != null) {
	//                EmployeeDto employeeDto = EMPLOYEE.modelToDto(appointment.getEmployee(),EmployeeDto.class);
	//                o.setEmployeeDto(employeeDto);
	//            }
	//            if(appointment.getOrder() != null){
	//                o.setOrderId(appointment.getOrder().getId());
	//            }
	//            return (U) o;
	//        }
	//
	//        @Override
	//        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
	//            Appointment o = super.dtoToModel(dto, clazz);
	//            AppointmentDto appointmentDto = (AppointmentDto) dto;
	//
	////            if(appointmentDto.getScheduleTime() != null){
	////                o.setScheduleTime(new Timestamp(appointmentDto.getScheduleTime()));
	////            }
	//
	//            if(appointmentDto.getId() != null){
	//                Optional<Appointment> dbAppointmentOptional = appointmentRepo.findById(appointmentDto.getId());
	//                if (dbAppointmentOptional.isPresent()) {
	//                    Appointment dbAppointment = dbAppointmentOptional.get();
	//
	//                    NullAwareBeanUtilsBean.get().copyProperties(o, dbAppointment);
	//                    NullAwareBeanUtilsBean.get().copyProperties(o, appointmentDto);
	//
	//                }
	//            }
	//            if (appointmentDto.getClientDto() != null && appointmentDto.getClientDto().getId() != null) {
	//                Client client = ModelDtoConvertor.CLIENT.dtoToModel(appointmentDto.getClientDto(), Client.class);
	//                o.setClient(client);
	//            }
	//
	//            if (appointmentDto.getEmployeeDto() != null && appointmentDto.getEmployeeDto().getId() != null){
	//                Employee employee = EMPLOYEE.dtoToModel(appointmentDto.getEmployeeDto(), Employee.class);
	//                o.setEmployee(employee);
	//            }
	//            if(appointmentDto.getOrderId() != null){
	//                Order order = new Order();
	//                order.setId(appointmentDto.getOrderId());
	//                o.setOrder(order);
	//            }
	//            return (U) o;
	//        }
	//    },
	//    SERVICE {
	//        @Override
	//        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
	//            ServiceDto o = super.modelToDto(model, clazz);
	//            Service service = (Service) model;
	//            if (service.getProductServices() != null) {
	//                List<ProductDto> productDtoList = new ArrayList<>();
	//
	//                for (ProductService productService : service.getProductServices()) {
	//                    Product product = productService.getProduct();
	//                    ProductDto productDto = PRODUCT.modelToDto(product, ProductDto.class);
	//                    productDtoList.add(productDto);
	//                }
	//                o.setProducts(productDtoList);
	//            }
	//            return (U) o;
	//        }
	//
	//        @Override
	//        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
	//            Service o = super.dtoToModel(dto, clazz);
	//            ServiceDto serviceDto = (ServiceDto) dto;
	//            Map<String, ProductService> productServiceMap = new HashMap<>();
	//            if(o.getId() != null){
	//                List<ProductService> productServiceList = productServiceRepository.findByService(o);
	//                productServiceList.forEach(productService -> {
	//                    String key = o.getId() + ":" + productService.getProduct().getId();
	//                    productServiceMap.put(key, productService);
	//                });
	//
	//            }
	//
	//            if (serviceDto.getProducts() != null) {
	//                List<ProductService> productServiceList = new ArrayList<>();
	//                for (ProductDto productDto : serviceDto.getProducts()) {
	//                    Product product = PRODUCT.dtoToModel(productDto, Product.class);
	//                    String key = o.getId() + ":" + productDto.getId();
	//                    if(productServiceMap.containsKey(key)){
	//                        productServiceList.add(productServiceMap.get(key));
	//                    }else{
	//                        ProductService productService = new ProductService();
	//                        productService.setService(o);
	//                        productService.setProduct(product);
	//                        productServiceList.add(productService);
	//                    }
	//                }
	//                o.setProductServices(productServiceList);
	//            }
	//            return (U) o;
	//        }
	//    },
	//		PRODUCT {
	//        @Override
	//        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
	//            ProductDto o = super.modelToDto(model, clazz);
	//            Product product = (Product) model;
	////            if (product.getProductServices() != null) {
	////                List<ServiceDto> serviceDtoList = new ArrayList<>();
	////
	////                for (ProductService productService : product.getProductServices()) {
	////                    Service service = productService.getService();
	////                    ServiceDto serviceDto = SERVICE.modelToDto(service, ServiceDto.class);
	////                    serviceDtoList.add(serviceDto);
	////                }
	////                o.setServices(serviceDtoList);
	////            }
	//            if(product.getCategory() != null){
	//                CategoryDto categoryDto = CATEGORY.modelToDto(product.getCategory(), CategoryDto.class);
	//                o.setCategoryDto(categoryDto);
	//            }
	////            if(product.getPartners() != null){
	////                PartnerDto partnerDto = GenericConvertor.PARTNER.modelToDto(product.getPartners(), PartnerDto.class);
	////                o.setPartnerDto(partnerDto);
	////            }
	//            return (U) o;
	//        }
	//
	//        @Override
	//        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
	//            Product product = super.dtoToModel(dto, clazz);
	//            ProductDto productDto = (ProductDto) dto;
	//            if (productDto.getOrder() != null) {
	//                List<OrderProduct> orderProducts = new ArrayList<>();
	//                OrderProduct orderProduct = new OrderProduct();
	//                Order mOrder = new Order();
	//                for (OrderDto order : productDto.getOrder()) {
	//                	orderProduct.setProduct(product);
	//                	mOrder.setId(order.getId());
	//                	orderProduct.setOrder(mOrder);
	//                	orderProducts.add(orderProduct);
	//                }
	//                product.setOrderProducts(orderProducts);
	//            }
	//            if (productDto.getCategoryDto() != null && productDto.getCategoryDto().getId() != null) {
	//                Category category = new Category();
	//                category.setId(productDto.getCategoryDto().getId());
	//                product.setCategory(category);
	//            }
	//            if (productDto.getPartnerDto() != null) {
	//                Partner partner = new Partner();
	//                partner.setId(productDto.getPartnerDto().getId());
	//                product.setPartners(partner);
	//            }
	//            return (U) product;
	//        }
	//    },
	//    CATEGORY {
	//        @Override
	//        public <T, U> U dtoToModel(T dto, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
	//            Category o = super.dtoToModel(dto, clazz);
	//            return (U) o;
	//        }
	//
	//        @Override
	//        public <T, U> U modelToDto(T model, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
	//            CategoryDto o = super.modelToDto(model, clazz);
	//            return (U) o;
	//
	//        }
	//    };

	//    private static AppointmentRepository appointmentRepo;
	//
	//    public static void setAppointmentRepo(AppointmentRepository appointmentRepo) {
	//        GenericConverter.appointmentRepo = appointmentRepo;
	//    }
	//    private static ProductServiceRepository productServiceRepository;
	//
	//    public static void setProductServiceRepository(ProductServiceRepository productServiceRepository) {
	//        GenericConverter.productServiceRepository = productServiceRepository;
	//    }
}