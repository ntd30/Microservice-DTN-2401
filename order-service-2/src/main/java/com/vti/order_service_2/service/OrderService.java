package com.vti.order_service_2.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.vti.order_service_2.entity.Order;
import com.vti.order_service_2.repository.IOrderRepository;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final IOrderRepository orderRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public Order addNewOrder(Order order) {
        // Get quantity stock
        Integer pQuantityStock = getIntegerValue(order.getProductCode() + "_quantity_stock");
        System.out.println("*********************Product quantity stock: " + pQuantityStock);

        // Calculate total sold
        String productSold = order.getProductCode() + "_sold";

        redisTemplate.opsForValue().setIfAbsent(productSold, 0);

        Object totalSold = redisTemplate.opsForValue().increment(productSold, order.getQuantity());
        System.out.println("*********************Total sold: " + totalSold);

        if (Integer.parseInt(totalSold.toString()) > pQuantityStock) {
            System.out.println("Out of stocks *********************");
            return null;
        }

        UUID uuid = UUID.randomUUID();
        order.setId(uuid);
        return orderRepository.save(order);
    }

    private Integer getIntegerValue(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        try {
            return Integer.parseInt(value.toString());
        } catch(NumberFormatException exception) {
            throw new IllegalArgumentException("Value for key " + key + " is not a valid integer");
        }
    }
    
}
