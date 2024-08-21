package com.vti.product_service.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.vti.product_service.dto.request.ProductCreationRequest;
import com.vti.product_service.dto.response.ProductResponse;
import com.vti.product_service.entity.Product;
import com.vti.product_service.repository.IProductRepositoy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final IProductRepositoy productRepositoy;
    private final ModelMapper modelMapper;

    public List<Product> getAllProducts() {
        return productRepositoy.findAll();
    }

    public ProductResponse addNewProduct(ProductCreationRequest request) {
        Product product = modelMapper.map(request, Product.class);
        productRepositoy.save(product);
        return modelMapper.map(product, ProductResponse.class);
    }
}
