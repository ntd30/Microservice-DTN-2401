package com.vti.order_service_2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.order_service_2.entity.Order;
import com.vti.order_service_2.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Order addNewOrder(@RequestBody Order order) {
        System.out.println("This is order-service-2");
        return orderService.addNewOrder(order);
    }

    @GetMapping
    public String getMethodName() {
        return "This is order-service-2";
    }
}
