package com.filetransclient.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filetransclient.config.KafkaTopicConfig;
import com.filetransclient.model.TransFileModel;
import com.google.gson.Gson;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerService {
	private Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaTopicConfig.class);
	
    public TransFileModel receiveFromKafka(String topicname) {
    	TransFileModel transFileModel = new TransFileModel();
        Properties props = new Properties();
        props.put("bootstrap.servers", (String)ctx.getBean("bootstrapServer"));
        props.put("key.deserializer", (String)ctx.getBean("keydeserializer"));
	    props.put("value.deserializer", (String)ctx.getBean("valuedeserializer"));
        props.put("group.id", (String)ctx.getBean("filetransgroup"));
        props.put("enable.auto.commit", (String)ctx.getBean("autocommit"));
        props.put("auto.offset.reset", (String)ctx.getBean("offsetreset"));
        props.put("", "true");
        props.put("", "latest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        consumer.subscribe(Arrays.asList(topicname));
        ObjectMapper mapper = new ObjectMapper();
        String message = "";
        Gson gson = new Gson();
        try {
        	ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100000));
        	for (ConsumerRecord<String, String> cr : records) {
                String str = cr.value();
                transFileModel = gson.fromJson(str, TransFileModel.class);
            }
        }catch (Exception e){
        	logger.error("receiveFromKafka error" + e);
        }finally {
            consumer.close();
        }
        
        return transFileModel;
    }
}