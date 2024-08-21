package com.vti.order_service_1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.order_service_1.entity.Product;
import com.vti.order_service_1.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Product addNewProduct(@RequestBody Product product) {
        return productService.addNewProduct(product);
    }
    
}
