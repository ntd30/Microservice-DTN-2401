package com.vti.rabbitmq_client.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.vti.rabbitmq_client.constants.Constants;

@Component
public class Consumer {
    @RabbitHandler
    @RabbitListener(queues = Constants.QUEUE_NAME_DEMO)
    public void receiverMessage(String message) {
        System.out.println("Message received from Rabbit Server: " + message);
    }
}
