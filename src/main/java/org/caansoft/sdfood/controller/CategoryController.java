package org.caansoft.sdfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.caansoft.sdfood.dto.CategoryDto;
import org.caansoft.sdfood.request.CategoryReq;
import org.caansoft.sdfood.service.CategoryService;
import org.caansoft.core.BaseRestMapper;
import org.caansoft.core.common.exception.CoreRuntimeException;
import org.caansoft.core.common.exception.RestErrorCodes;
import org.caansoft.core.dto.response.Response;
@RestController
public class CategoryController implements BaseRestMapper {
    @Autowired
    CategoryService categoryService;

    @PostMapping("categories")
    @ResponseBody
    ResponseEntity<Response<CategoryDto>> addProduct(@RequestBody CategoryReq categoryReq) {
        CategoryDto add = categoryService.add(categoryReq);
        return new ResponseEntity<Response<CategoryDto>>(new Response<>(add), HttpStatus.CREATED);
    }

    @GetMapping("categories/{id}")
    ResponseEntity<Response<CategoryDto>> getProduct(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.findOne(id);
        if (categoryDto == null)
            throw new CoreRuntimeException(RestErrorCodes.NOT_FOUND.message, RestErrorCodes.NOT_FOUND.code, HttpStatus.NOT_FOUND);
        return new ResponseEntity<Response<CategoryDto>>(new Response<>(categoryDto), HttpStatus.OK);
    }

    @GetMapping("categories")
    @ResponseBody
    ResponseEntity<Response<Page<CategoryDto>>> getAllProducts(Pageable pageable) {
        Page<CategoryDto> dto = categoryService.find(pageable);
        return new ResponseEntity<Response<Page<CategoryDto>>>(new Response<>(dto), HttpStatus.OK);
    }

    @DeleteMapping("categories/{id}")
    ResponseEntity<Response> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return new ResponseEntity<Response>(new Response(null, "Delete success,", "200"), HttpStatus.OK);
    }

    @PutMapping("categories/{id}")
    @ResponseBody
    ResponseEntity<Response<CategoryDto>> update(@RequestBody CategoryReq categoryReq) {
        CategoryDto categoryDto = categoryService.update(categoryReq);
        return new ResponseEntity<Response<CategoryDto>>(new Response<>(categoryDto), HttpStatus.OK);
    }

}
