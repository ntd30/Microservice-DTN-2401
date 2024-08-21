package com.vti.rabbitmq_client.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.vti.rabbitmq_client.constants.Constants;

@Configuration
@Service
public class RabbitQueueDefine {
    @Autowired
    private AmqpAdmin rAmqpAdmin;

    @Bean
    public Queue initQueue() {
        Queue queue = new Queue(Constants.QUEUE_NAME_DEMO);
        rAmqpAdmin.declareQueue(queue);
        return null;
    }
}
