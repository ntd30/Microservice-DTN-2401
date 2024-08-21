package com.vti.order_service_1.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.vti.order_service_1.entity.Product;
import com.vti.order_service_1.repository.IProductRepository;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final IProductRepository productRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public Product addNewProduct(Product product) {
        UUID uuid = UUID.randomUUID();
        product.setId(uuid);

        String redisQuantityStockKey = product.getProductCode() + "_quantity_stock";

        if (redisTemplate.opsForValue().get(redisQuantityStockKey) == null) {
            redisTemplate.opsForValue().set(redisQuantityStockKey, 1);
        } else {
            redisTemplate.opsForValue().increment(redisQuantityStockKey);
        }

        return productRepository.save(product);
    }
}
