package com.vti.load_balancer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping(value="/consummer-order")
public class LoadBalancerController {
    private RestTemplate restTemplate;

    public LoadBalancerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String consummerOrders() {
        return restTemplate.getForObject("http://order-service/orders", String.class);
    }

    // @PostMapping
    // public String postMethodName() {
    //     return restTemplate.getForObject("http://order-service/orders", String.class);
    // }
    
}
