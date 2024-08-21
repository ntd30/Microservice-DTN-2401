package com.vti.order_service_1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.order_service_1.entity.Order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/test-ab")
public class TestABController {
    @GetMapping
    public String getMethodName() {
        System.out.println("Apache benchmark");
        return "Response from Apache Benchmark";
    }
    
    @PostMapping
    public String postMethodName(@RequestBody Order order) {
        System.out.println("Order object from apache benchmark client request***********");
        System.out.println(order);
        return "Response from Apache Benchmark";
    }
    
}

