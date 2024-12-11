package org.caansoft.sdfood.controller;

import org.caansoft.core.BaseRestMapper;
import org.caansoft.core.common.exception.CoreRuntimeException;
import org.caansoft.core.common.exception.RestErrorCodes;
import org.caansoft.core.dto.response.Response;
import org.caansoft.core.enums.Flag;
import org.caansoft.sdfood.dto.ProductStockQuantityDTO;
import org.caansoft.sdfood.dto.StockCountHeaderDto;
import org.caansoft.sdfood.dto.StockCountLineItemDto;
import org.caansoft.sdfood.enums.StockCountStatus;
import org.caansoft.sdfood.model.StockCountHeader;
import org.caansoft.sdfood.prestashop.dto.TaxRuleGroupDto;
import org.caansoft.sdfood.service.AdjustmentService;
import org.caansoft.sdfood.service.StockCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by shoaib on 07/03/2023.
 */
@RestController
public class StockCountController implements BaseRestMapper {

    @Autowired
    private StockCountService stockCountService;
    
    @Autowired
    private AdjustmentService adjustmentService;

    @GetMapping("stock-counts")
    ResponseEntity<Response<Page<StockCountHeaderDto>>> find(@RequestParam(required = false) Long startDate,
                                                             @RequestParam(required = false) Long endDate,
                                                             @RequestParam(required = false, defaultValue = "ACTIVE") String flag,
                                                             @RequestParam(required = false) StockCountStatus status, Pageable pageable) {
        StockCountHeader header = new StockCountHeader();
        header.setFlag(Flag.valueOf(flag));
        Date fromDate = null;
        Date toDate = null;

        try {
            fromDate = startDate != null ? new Date(startDate) : null;
            toDate = endDate != null ? new Date(endDate) : null;
        } catch (Exception ex) {
            throw new CoreRuntimeException(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        Page<StockCountHeaderDto> headerDtos = stockCountService.find(header, fromDate, toDate, status, pageable);
        return new ResponseEntity<Response<Page<StockCountHeaderDto>>>(new Response<>(headerDtos), HttpStatus.OK);
    }


    @GetMapping("stock-counts/{id}")
    ResponseEntity<Response<StockCountHeaderDto>> findOne(@PathVariable Long id) {
        StockCountHeaderDto headerDto = stockCountService.findOne(id);
        return new ResponseEntity<Response<StockCountHeaderDto>>(new Response<>(headerDto), HttpStatus.OK);
    }


    @PostMapping("stock-counts")
    @ResponseBody
    ResponseEntity<Response<StockCountHeaderDto>> add(@RequestBody StockCountHeaderDto dto) {
        StockCountHeaderDto headerDto = stockCountService.initiateStockCount();
        return new ResponseEntity<Response<StockCountHeaderDto>>(new Response<>(headerDto), HttpStatus.CREATED);
    }


    @PutMapping("stock-counts/{id}")
    ResponseEntity<Response<Void>> update(@PathVariable Long id, @RequestBody StockCountHeaderDto dto) {
        stockCountService.update(id, dto);
        return new ResponseEntity<Response<Void>>(new Response<>(null), HttpStatus.NO_CONTENT);
    }

    @PostMapping("stock-counts/{id}/line-items")
    ResponseEntity<Response<StockCountLineItemDto>> addLineItem(@PathVariable Long id, @RequestBody StockCountLineItemDto dto) {
        StockCountLineItemDto stockCountLineItemDto = null;
        try {
            stockCountLineItemDto = stockCountService.addLineItem(id, dto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return new ResponseEntity<Response<StockCountLineItemDto>>(new Response<>(stockCountLineItemDto), HttpStatus.OK);
    }
    
    @GetMapping("products/stocks")
    @ResponseBody
    ResponseEntity<Response<Page<ProductStockQuantityDTO>>> findProductStock(
    		@RequestParam (required=false) String productName,
    		@RequestParam (required=false) Long startDate,
    		@RequestParam (required=false) Long endDate,
    		@RequestParam(required = false, defaultValue = "ACTIVE") String flag,
    		@RequestParam(required = false, defaultValue = "true") Boolean isPageable, Pageable pageable
    		) 
    {
    	Page<ProductStockQuantityDTO> list = adjustmentService.getProductStockCount(productName, startDate, endDate, Flag.valueOf(flag), pageable, isPageable);
    	return new ResponseEntity<Response<Page<ProductStockQuantityDTO>>>(new Response<Page<ProductStockQuantityDTO>>(list), HttpStatus.OK); 
    }
    
}
