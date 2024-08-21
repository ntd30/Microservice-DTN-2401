package com.vti.order_service_1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.order_service_1.entity.Order;
import com.vti.order_service_1.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Order addNewOrder(@RequestBody Order order) {
        System.out.println("This is os 1");
        return orderService.addNewOrder(order);
    }

    @GetMapping
    public String getMethodName() {
        return "This is order-service-1";
    }
    
}
