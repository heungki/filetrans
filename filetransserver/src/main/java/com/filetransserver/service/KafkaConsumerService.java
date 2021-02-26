package com.filetransserver.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerService {
	
    @KafkaListener(topics = "filetrans-topic")
    public void listenGroupFoo(String message) {
        System.out.println("received message foo : " + message);
    }
}