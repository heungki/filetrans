package com.filetransserver.controller;

import com.filetransserver.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    private final KafkaProducerService producer;

    @Autowired
    KafkaController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/topictest")
    String send() {
    	producer.sendMessage("filetrans-topic", "message key filetrans", "filetrans -> test message");
    	//producer.sendMessage("company-jjeaby-topic", "message key company", "company -> jjeaby message");
        return "Kafka Produce!!!";
    }
}