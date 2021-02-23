package com.filetransserver.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "${spring.kafka.template.filetrans-topic}", containerFactory = "fileTransContainerFactory", groupId = "${spring.kafka.consumer.filetrans-group-id}")
    public void listenFileTransTopic(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String messageKey) throws Exception {
        System.out.println("Topic: [medium-jjeaby-topic] messageKey Message: [" + messageKey + "]");
        System.out.println("Topic: [medium-jjeaby-topic] Received Message: [" + message + "] from partition: [" + partition + "]");
    }
}