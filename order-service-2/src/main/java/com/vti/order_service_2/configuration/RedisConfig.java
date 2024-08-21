package com.vti.order_service_2.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {
    // Create connection between my app with redis server
    @Bean
    public JedisConnectionFactory JedisConnectionFactory() {
        // Address of redis server
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
        config.setPassword("123abc");

        // Connect from my app to redis server
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().build();
        
        return new JedisConnectionFactory(config, clientConfig);
    }

    // Used to interact with redis server (CRUD)
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Create connection
        template.setConnectionFactory(JedisConnectionFactory());

        // Convert object (value) to string before save in redis
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }
}
