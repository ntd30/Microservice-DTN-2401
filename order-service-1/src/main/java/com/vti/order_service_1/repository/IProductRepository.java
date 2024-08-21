package com.vti.order_service_1.repository;

import org.springframework.data.repository.CrudRepository;

import com.vti.order_service_1.entity.Product;

import java.util.UUID;

public interface IProductRepository extends CrudRepository<Product, UUID> {
    
}
