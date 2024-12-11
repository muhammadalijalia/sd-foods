package org.caansoft.sdfood.controller;

import org.caansoft.core.BaseRestMapper;
import org.caansoft.core.common.exception.CoreRuntimeException;
import org.caansoft.core.common.exception.RestErrorCodes;
import org.caansoft.core.dto.response.Response;
import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.dto.ProductDto;
import org.caansoft.sdfood.model.Category;
import org.caansoft.sdfood.model.Product;
import org.caansoft.sdfood.request.ProductReq;
import org.caansoft.sdfood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class ProductController implements BaseRestMapper {
    @Autowired
    ProductService productService;

    @PostMapping("products")
    @ResponseBody
    ResponseEntity<Response<ProductDto>> addProduct(@RequestBody ProductReq productReq) {
        ProductDto add = productService.add(productReq);
        return new ResponseEntity<Response<ProductDto>>(new Response<>(add), HttpStatus.CREATED);
    }

    @GetMapping("products/{id}")
    ResponseEntity<Response<ProductDto>> getProduct(@PathVariable Long id) {
        ProductDto productDto = productService.findOne(id);
        if (productDto == null)
            throw new CoreRuntimeException(RestErrorCodes.NOT_FOUND.message, RestErrorCodes.NOT_FOUND.code, HttpStatus.NOT_FOUND);
        return new ResponseEntity<Response<ProductDto>>(new Response<>(productDto), HttpStatus.OK);
    }
    @GetMapping("products")
    @ResponseBody
    ResponseEntity<Response<Page<ProductDto>>> search(@RequestParam(required = false) String name, @RequestParam(required = false, defaultValue = "ACTIVE") String flag,
                                                     @RequestParam(required = false) String categoryName, Pageable pageable) {
        Product product = new Product();
        product.setFlag(Flag.valueOf(flag));
        product.setName(name);
        if (categoryName != null) {
            Category category = new Category();
            category.setName(categoryName);
            product.setCategory(category);
        }

        Page<ProductDto> productDtos = productService.find(product, null, null, pageable);
        return new ResponseEntity<Response<Page<ProductDto>>>(new Response<>(productDtos), HttpStatus.OK);
    }
    
    @GetMapping("products/all")
    @ResponseBody
    ResponseEntity<Response<Page<ProductDto>>> searchAll(@RequestParam(required = false) String name, @RequestParam(required = false, defaultValue = "ACTIVE") String flag,
                                                     @RequestParam(required = false) String categoryName, Pageable pageable) {
        Product product = new Product();
        product.setFlag(Flag.valueOf(flag));
        product.setName(name);
        if (categoryName != null) {
            Category category = new Category();
            category.setName(categoryName);
            product.setCategory(category);
        }

        Page<ProductDto> productDtos = productService.find(product, null, null, Pageable.unpaged());
        return new ResponseEntity<Response<Page<ProductDto>>>(new Response<>(productDtos), HttpStatus.OK);
    }

     @DeleteMapping("products/{id}")
    ResponseEntity<Response> delete(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseEntity<Response>(new Response(null, "Delete success,", "200"), HttpStatus.OK);
    }

    @PutMapping("products/{id}")
    @ResponseBody
    ResponseEntity<Response<ProductDto>> update(@RequestBody ProductReq productReq) {
        ProductDto dto = productService.update(productReq);
        return new ResponseEntity<Response<ProductDto>>(new Response<>(dto), HttpStatus.OK);
    }
    
    @GetMapping("productsearch")
    @ResponseBody
    ResponseEntity<Response<Page<ProductDto>>> search(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String categoryName,
                                                      @RequestParam(required = false) Long startDate,
                                                      @RequestParam(required = false) Long endDate, Pageable pageable){
    	 
    	 Product product = new Product();
    	 product.setName(name);
         if(categoryName != null) {
             Category category = new Category();
             category.setName(categoryName);
             product.setCategory(category);
         }

//    	 OrderProductLocation orderProductLocation = new OrderProductLocation();
//    	 orderProductLocation.setExpiry(null);
//    	 Date expDate = null;
    	 Date fromDate = null;
         Date toDate = null;

         try {
             fromDate = startDate != null ? new Date(startDate) : null;
             toDate = endDate != null ? new Date(endDate) : null;

         } catch (Exception ex){
             throw new CoreRuntimeException(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
         }
    	
    	 Page<ProductDto> productDtos = productService.find(product, fromDate, toDate, pageable);
         return new ResponseEntity<Response<Page<ProductDto>>>(new Response<>(productDtos),HttpStatus.OK);
    }
        

}

