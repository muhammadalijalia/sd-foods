package org.caansoft.sdfood.controller;

import org.caansoft.core.BaseRestMapper;
import org.caansoft.core.common.exception.CoreRuntimeException;
import org.caansoft.core.common.exception.RestErrorCodes;
import org.caansoft.core.dto.response.Response;
import org.caansoft.core.enums.Flag;
import org.caansoft.core.model.Partner;
import org.caansoft.sdfood.dto.InventoryOrderedDto;
import org.caansoft.sdfood.dto.OrderDto;
import org.caansoft.sdfood.enums.OrderStatus;
import org.caansoft.sdfood.model.Order;
import org.caansoft.sdfood.request.OrderReq;
import org.caansoft.sdfood.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
public class OrderController implements BaseRestMapper {

    @Autowired
    private OrderService orderService;   

    @PostMapping("orders")
    @ResponseBody
    ResponseEntity<Response<OrderDto>> add(@RequestBody OrderReq orderReq){
    	
    		OrderDto orderDto = orderService.add(orderReq);
    		return new ResponseEntity<Response<OrderDto>>(new Response<>(orderDto), HttpStatus.CREATED);
    }

    @GetMapping("orders/{id}")
    ResponseEntity<Response<OrderDto>> getOrder(@PathVariable Long id){
        OrderDto orderDto = orderService.findOne(id);
        if(orderDto==null)
            throw new CoreRuntimeException(RestErrorCodes.NOT_FOUND.message,RestErrorCodes.NOT_FOUND.code,HttpStatus.NOT_FOUND );
            return new ResponseEntity<Response<OrderDto>>(new Response<>(orderDto),HttpStatus.OK);
    }

    @GetMapping("orders")
    ResponseEntity<Response<Page<OrderDto>>> find(@RequestParam(required = false) String orderNo, @RequestParam(required = false) Long startDate,
                                                  @RequestParam(required = false) Long endDate, @RequestParam(required = false, defaultValue = "ACTIVE") String flag, @RequestParam(required = false) OrderStatus status,
                                                  @RequestParam(required = false, defaultValue = "false") boolean project,
                                                  @RequestParam(required = false) String productName,
                                                  @RequestParam(required = false) Long vendorId, @RequestParam(required = false) String logisticType,
                                                  @RequestParam(required = false) Long startDeliveryDate,
                                                  @RequestParam(required = false) Long endDeliveryDate, Pageable pageable){
        Order order = new Order();
        order.setFlag(Flag.valueOf(flag));
        order.setNumber(orderNo);
        order.setLogisticsType(logisticType);
//        if(deliveryDate != null){
//            Timestamp deliveryDateTimestamp = new Timestamp(deliveryDate);
//            order.setDeliveryDate(deliveryDateTimestamp);
//        }
        Partner partner = new Partner();
        partner.setType(null);
        partner.setId(vendorId);
        order.setPartners(partner);

//        order.setClient(client);
        Date fromDate = null;
        Date toDate = null;
        Date startDelDate =  null;
        Date endDelDate = null;

        try {
            fromDate = startDate != null ? new Date(startDate) : null;
            toDate = endDate != null ? new Date(endDate) : null;
            startDelDate = startDeliveryDate != null ? new Date(startDeliveryDate) : null;
            endDelDate = endDeliveryDate != null ? new Date(endDeliveryDate) : null;


        } catch (Exception ex){
            throw new CoreRuntimeException(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }
        Page<OrderDto> orderDtos = orderService.find(order, fromDate, toDate, status, project, productName, startDelDate, endDelDate, pageable);
        return new ResponseEntity<Response<Page<OrderDto>>>(new Response<>(orderDtos),HttpStatus.OK);
    }

    @PutMapping("orders/{id}")
    @ResponseBody
    ResponseEntity<Response<OrderDto>> update (@RequestBody OrderReq orderReq){
    		OrderDto orderDto=orderService.update(orderReq);
    		return new ResponseEntity<Response<OrderDto>>(new Response<>(orderDto),HttpStatus.OK);
    }

    @DeleteMapping("orders/{id}")
    ResponseEntity delete(@PathVariable Long id) {
    		orderService.delete(id);
            return new ResponseEntity(id, HttpStatus.OK);
    }
    
    @GetMapping("orders/inventory")
    @ResponseBody
    ResponseEntity<Response<Page<InventoryOrderedDto>>> inventoryOrdered (
    		@RequestParam(required = false) Long startDate, 
    		@RequestParam(required = false) Long endDate,
    		@RequestParam(required = false) String productName,
    		@RequestParam(required = false) String vendorName,
    		@RequestParam(required = false, defaultValue = "true") Boolean isPageable, 
    		Pageable pageable
    		) {
    	Page<InventoryOrderedDto> list = orderService.getInventoryOrdered(startDate, endDate, productName, vendorName, isPageable, pageable);
    	return new ResponseEntity<Response<Page<InventoryOrderedDto>>>(new Response<Page<InventoryOrderedDto>>(list), HttpStatus.OK);
    }

}
