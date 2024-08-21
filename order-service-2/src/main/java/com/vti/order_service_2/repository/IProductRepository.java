package com.vti.order_service_2.repository;

import org.springframework.data.repository.CrudRepository;

import com.vti.order_service_2.entity.Product;

import java.util.UUID;

public interface IProductRepository extends CrudRepository<Product, UUID> {
    
}
