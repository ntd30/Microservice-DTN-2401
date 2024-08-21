package com.vti.product_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vti.product_service.dto.request.ProductCreationRequest;
import com.vti.product_service.dto.response.ProductResponse;
import com.vti.product_service.entity.Product;
import com.vti.product_service.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    
    @PostMapping
    public ProductResponse addNewProduct(@RequestBody ProductCreationRequest request) {
       return productService.addNewProduct(request);
    }
    
}
