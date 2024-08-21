package com.vti.rabbitmq_client.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.vti.rabbitmq_client.constants.Constants;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/message-pushing")
    public ResponseEntity<Object> sendMessage(@RequestParam(defaultValue = "Testing push message") String message) {
        rabbitTemplate.convertAndSend(Constants.QUEUE_NAME_DEMO, message);
        return new ResponseEntity<>("Message pushed: " + message, HttpStatus.OK);
    }
    
}
