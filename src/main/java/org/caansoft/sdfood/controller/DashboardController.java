package org.caansoft.sdfood.controller;

import org.caansoft.core.BaseRestMapper;
import org.caansoft.core.common.exception.CoreRuntimeException;
import org.caansoft.core.dto.response.Response;
import org.caansoft.core.enums.Flag;
import org.caansoft.core.service.UserService;
import org.caansoft.sdfood.dto.StatsDto;
import org.caansoft.sdfood.model.Order;
import org.caansoft.sdfood.repo.OrderRepository;
import org.caansoft.sdfood.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class DashboardController implements BaseRestMapper {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private UserService userService;


    @GetMapping("orders/dashboard")
    ResponseEntity<Response<StatsDto>> dashboardStats(@RequestParam(required = false) Long startDate, @RequestParam(required = false) Long endDate, @RequestParam(required = false, defaultValue = "ACTIVE") String flag, @RequestParam(required = false, defaultValue = "all") String dateType) {
        logger.info("dashboardStats");
        Order order = new Order();
        order.setFlag(Flag.valueOf(flag));

        Date fromDate = null;
        Date toDate = null;

        try {
            fromDate = startDate != null ? new Date(startDate) : null;
            toDate = endDate != null ? new Date(endDate) : null;
        } catch (Exception ex) {
            throw new CoreRuntimeException(String.valueOf(HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
        }

        StatsDto stats = orderService.dashboard(order, fromDate, toDate, Pageable.unpaged());
        return new ResponseEntity<>(new Response<>(stats), HttpStatus.OK);
    }


}
