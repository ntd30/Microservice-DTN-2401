package com.vti.order_service_2.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

import java.util.UUID;

@RedisHash("Product")
@Data
public class Product {
    @Id
    private UUID id;
    private String productName;
    private String productCode;
    private String description;
}
