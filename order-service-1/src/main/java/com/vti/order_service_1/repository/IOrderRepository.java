package com.vti.order_service_1.repository;

import org.springframework.data.repository.CrudRepository;

import com.vti.order_service_1.entity.Order;

import java.util.UUID;

public interface IOrderRepository extends CrudRepository<Order, UUID>{
    
}
