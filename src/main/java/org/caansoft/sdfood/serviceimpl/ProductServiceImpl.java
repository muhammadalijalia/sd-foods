package org.caansoft.sdfood.serviceimpl;

import org.caansoft.core.enums.AccessLevelEnum;
import org.caansoft.core.enums.Flag;
import org.caansoft.core.model.Employee;
import org.caansoft.core.model.Permission;
import org.caansoft.core.model.Role;
import org.caansoft.core.model.User;
import org.caansoft.core.service.UserService;
import org.caansoft.sdfood.dto.CategoryDto;
import org.caansoft.sdfood.model.*;
import org.caansoft.sdfood.prestashopIntegration.PrestashopProductDto;
import org.caansoft.sdfood.prestashopIntegration.PrestashopService;
import org.caansoft.sdfood.repo.PrestashopProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.caansoft.sdfood.dto.OrderDto;
import org.caansoft.sdfood.dto.ProductDto;
import org.caansoft.sdfood.enums.ModelDtoConvertor;
import org.caansoft.sdfood.enums.OrderStatus;
import org.caansoft.sdfood.helper.GenericConverter;
import org.caansoft.sdfood.metamodel.OrderProduct_;
import org.caansoft.sdfood.metamodel.Product_;
import org.caansoft.sdfood.repo.OrderProductLocationRepository;
import org.caansoft.sdfood.repo.ProductRepository;
import org.caansoft.sdfood.service.ProductService;
import org.caansoft.core.common.exception.CoreRuntimeException;
import org.caansoft.core.dto.BaseDto;

import javax.mail.Flags;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	OrderProductLocationRepository orderProductLocationRepository;

    @Autowired
    private UserService userService;

	@Autowired
	private PrestashopService prestashopService;

	@Autowired
	private PrestashopProductRepo prestashopProductRepository;

	@Override
	public <T extends BaseDto> T add(T dto) {
		ProductDto productDto = (ProductDto) dto;
		try {
			Product model = ModelDtoConvertor.PRODUCT.dtoToModel(productDto, Product.class);
			if(model.getHtPrice() != null){
				BigDecimal bd = new BigDecimal(Double.toString(model.getHtPrice()));
				bd = bd.setScale(2, RoundingMode.HALF_UP);
				model.setHtPrice(bd.floatValue());
			}
			model = productRepository.save(model);
			productDto.setId(model.getId());

//			PrestashopProductDto prestashopProductDto = prestashopService.addProduct(model);
//			model.setPrestashopProductId(prestashopProductDto.getId());
//			productRepository.save(model);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) productDto;
	}


	@Override
	public <T extends BaseDto> Page<T> find(Pageable pageable) {
		Page<Product> all = productRepository.findAllByFlag(pageable, Flag.ACTIVE);
		if (all.getSize() > 0) {
			List<ProductDto> list = new ArrayList<>();
			for (Product product : all) {
				try {
					ProductDto dto = ModelDtoConvertor.PRODUCT.modelToDto(product, ProductDto.class);
					list.add(dto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return (Page<T>) new PageImpl<ProductDto>(list, pageable, all.getTotalElements());
		}
		return (Page<T>) new PageImpl<ProductDto>(new ArrayList<>());
	}

	@Override
	public <T extends BaseDto> T findOne(long id) {
		Optional<Product> optional = productRepository.findById(id);
		if (optional.isPresent() && optional.get().getFlag() == Flag.ACTIVE) {
			try {
				return ModelDtoConvertor.PRODUCT.modelToDto(optional.get(), ProductDto.class);
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}
		return null;
	}


	@Override
	public void delete(long id) {
		Product product = productRepository.findById(id).get();
		product.setFlag(Flag.DELETED);
		productRepository.save(product);
	}

	@Override
	public <T extends BaseDto> T update(T dto) {
		ProductDto productDto = (ProductDto) dto;
		try {
			Product product = ModelDtoConvertor.PRODUCT.dtoToModel(dto, Product.class);
			if(product.getHtPrice() != null){
				BigDecimal bd = new BigDecimal(Double.toString(product.getHtPrice()));
				bd = bd.setScale(2, RoundingMode.HALF_UP);
				product.setHtPrice(bd.floatValue());
			}
			product = productRepository.save(product);
			if(productDto.getAction() != null && productDto.getAction().equalsIgnoreCase("PUBLISH")){
				PrestashopProductDto prestashopProductDto = prestashopService.addProduct(product);
				if(prestashopProductDto != null){
					product.setPrestashopProductId(prestashopProductDto.getId());
					productRepository.save(product);
					productDto.setPrestashopProductId(prestashopProductDto.getId());

					PrestashopProduct prestashopProduct = new PrestashopProduct();
					prestashopProduct.setId(prestashopProductDto.getId().longValue());
//					if(!prestashopProductDto.getName().isEmpty() && !prestashopProductDto.getName().get(0).getLanguage().isEmpty()){
//						prestashopProduct.setName(prestashopProductDto.getName().get(0).getLanguage().get(0).getName());
//					}
					prestashopProduct.setName(product.getName());
					prestashopProductRepository.save(prestashopProduct);

				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return (T)productDto;
	}


	@Override
	public <T extends BaseDto> Page<T> find(Product product, Date fromDate, Date toDate, Pageable pageable) {
		// TODO Auto-generated method stub
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username.isEmpty() || username.equals("anonymousUser")) {
            throw new CoreRuntimeException(HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED);
        }
//        User loggedInUser = userService.findUser(username);
//
//        if (loggedInUser.getEmployee() == null || loggedInUser.getEmployee().getJob() == null) {
//            throw new CoreRuntimeException(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.toString(), HttpStatus.FORBIDDEN);
//        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withNullHandler(ExampleMatcher.NullHandler.IGNORE)
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Product> example = Example.of(product, matcher);
        Page<Product> products = null;

//        orderProductLocationRepository.findAllByExpiryLessThanEqualAndExpiryGreaterThanEqual(toDate, fromDate)
        products = productRepository.findAll(getSpecFromDatesAndExample(fromDate, toDate, example), pageable);
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product dbproduct : products) {
            try {
            	productDtoList.add(ModelDtoConvertor.PRODUCT.modelToDto(dbproduct, ProductDto.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        return (Page<T>) new PageImpl<ProductDto>(productDtoList, pageable, products.getTotalElements());
	}
//	OrderProductLocation orderProductLocationobj = new OrderProductLocation();
    private Specification<Product> getSpecFromDatesAndExample(Date fromDate, Date toDate, Example<Product> example) {

        return (Specification<Product>) (root, query, builder) -> {
        	
            final List<Predicate> predicates = new ArrayList<>();
            if (fromDate != null) {

                predicates.add(builder.greaterThanOrEqualTo(root.join("orderProducts").join("orderProductsLocations").get("expiry"), fromDate));
            }
            if (toDate != null) {
                predicates.add(builder.lessThanOrEqualTo(root.join("orderProducts").join("orderProductsLocations").get("expiry"), toDate));
            }

//			if (categoryName != null) {
//				Join<Product, Category>  productCategoryJoin = root.join("categories");
//				predicates.add(builder.like(productCategoryJoin.get("name"), "%" + categoryName + "%"));
//			}


//            if (expDate != null) {
//                predicates.add(builder.greaterThanOrEqualTo(root.get("signedContractDate"), expDate));
//            }


            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));

            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }
    
}
