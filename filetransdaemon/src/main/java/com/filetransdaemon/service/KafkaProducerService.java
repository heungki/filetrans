package com.filetransdaemon.service;

import java.util.Properties;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.filetransdaemon.config.KafkaTopicConfig;
import com.filetransdaemon.service.KafkaProducerService;

@Service
public class KafkaProducerService {    
private static Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaTopicConfig.class);
	
	@Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;	
	
    public KafkaProducerService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
	public RecordMetadata sendMessage(String topicname, String ftmjson) throws Exception {		
		Properties properties = new Properties();
		properties.put("bootstrap.servers", (String)ctx.getBean("bootstrapServer"));
	    properties.put("key.serializer", (String)ctx.getBean("keyserializer"));
	    properties.put("value.serializer", (String)ctx.getBean("valueserializer"));
	    
	    RecordMetadata result;
	    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
	    
	    logger.info("sendMessage topicname : " + topicname);;
	    logger.info("sendMessage message : " + ftmjson);;
        try{
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topicname, ftmjson);
            
            // Message Àü¼Û
            Future<RecordMetadata> recordMetadata = producer.send(producerRecord);
            
            result = recordMetadata.get();
            
        }catch (Exception ex){
        	logger.info("sendMessage error : " + ex);;
            throw new Exception("sendMessage error : " + ex);
        }
        // flush data
 		producer.flush();

 		// flush and close producer
 		producer.close(); 
	    
	    return result;
	}
}