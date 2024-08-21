package com.vti.product_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vti.product_service.entity.Product;

public interface IProductRepositoy extends JpaRepository<Product, UUID> {
    
}
