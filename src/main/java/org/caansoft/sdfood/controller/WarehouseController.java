package org.caansoft.sdfood.controller;

import org.caansoft.core.BaseRestMapper;
import org.caansoft.core.common.exception.CoreRuntimeException;
import org.caansoft.core.common.exception.RestErrorCodes;
import org.caansoft.core.dto.response.Response;
import org.caansoft.sdfood.dto.WarehouseDto;
import org.caansoft.sdfood.request.WarehouseReq;
import org.caansoft.sdfood.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarehouseController implements BaseRestMapper{
	@Autowired
    WarehouseService warehouseService;
	
	@PostMapping("warehouses")
    @ResponseBody
    ResponseEntity<Response<WarehouseDto>> addWarehouse(@RequestBody WarehouseReq warehouseReq) {
		WarehouseDto add = warehouseService.add(warehouseReq);
        return new ResponseEntity<Response<WarehouseDto>>(new Response<>(add), HttpStatus.CREATED);
    }
	
    @GetMapping("warehouses/{id}")
    ResponseEntity<Response<WarehouseDto>> getProduct(@PathVariable Long id) {
    	WarehouseDto warehouseDto = warehouseService.findOne(id);
        if (warehouseDto == null)
            throw new CoreRuntimeException(RestErrorCodes.NOT_FOUND.message, RestErrorCodes.NOT_FOUND.code, HttpStatus.NOT_FOUND);
        return new ResponseEntity<Response<WarehouseDto>>(new Response<>(warehouseDto), HttpStatus.OK);
    }
    
    @GetMapping("warehouses")
    @ResponseBody
    ResponseEntity<Response<Page<WarehouseDto>>> getAllProducts(Pageable pageable) {
        Page<WarehouseDto> dto = warehouseService.find(pageable);
        return new ResponseEntity<Response<Page<WarehouseDto>>>(new Response<>(dto), HttpStatus.OK);
    }

    @DeleteMapping("warehouses/{id}")
    ResponseEntity<Response> delete(@PathVariable Long id) {
        warehouseService.delete(id);
        return new ResponseEntity<Response>(new Response(null, "Delete success,", "200"), HttpStatus.OK);
    }

    @PutMapping("warehouses/{id}")
    @ResponseBody
    ResponseEntity<Response<WarehouseDto>> update(@RequestBody WarehouseReq warehouseReq) {
        WarehouseDto warehouseDto = warehouseService.update(warehouseReq);
        return new ResponseEntity<Response<WarehouseDto>>(new Response<>(warehouseDto), HttpStatus.OK);
    }

}
