package com.filetransclient.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteConsumerGroupsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.internals.ProduceRequestResult;
import org.apache.kafka.common.KafkaFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.proxy.Callback;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

import com.filetransclient.controller.FileTransController;

@Service
public class KafkaTopicService {
	private Logger logger = LoggerFactory.getLogger(KafkaTopicService.class);
	ApplicationContext ctx = new AnnotationConfigApplicationContext(KafkaClientConfig.class);
	private String bootstrapServer;
	private int sessiontimeout;
	private int connectiontimeout;
	
	public String getBootstrapServer() {		
		bootstrapServer = (String)ctx.getBean("bootstrapServer");
		logger.info("bootstrapServer : " + bootstrapServer);
		
		return bootstrapServer;	
	}
	
	public CreateTopicsResult createTopic(Map<String, String> filetransinfo) throws Exception {		
		Properties properties = new Properties();
		properties.put("bootstrap.servers", (String)ctx.getBean("bootstrapServer"));
	    properties.put("connections.max.idle.ms", (int)ctx.getBean("sessiontimeout"));
	    properties.put("request.timeout.ms", (int)ctx.getBean("connectiontimeout"));
	    
	    AdminClient client = AdminClient.create(properties);
	    
	    CreateTopicsResult result;
	    
		String topicname=filetransinfo.get("topicname");
		try{
			result = client.createTopics(Arrays.asList(
                new NewTopic(topicname, 1, (short) 1)
                //new NewTopic("global-id-topic", 1, (short) 1),
                //new NewTopic("snapshot-topic", 1, (short) 1)
		    ));
	    
			logger.info("createTopic topicname: " + topicname);
	    }catch (Exception ex)
        {
        	logger.error("createTopic error: " + ex);
            throw new Exception(ex.toString());
        }
	    
	    //result.all().get();
	    client.close();
	    
	    return result;
	}
	
	public RecordMetadata fileTransReq(Map<String, String> filetransinfo) throws Exception {		
		Properties properties = new Properties();
		properties.put("bootstrap.servers", (String)ctx.getBean("bootstrapServer"));
	    properties.put("key.serializer", (String)ctx.getBean("keyserializer"));
	    properties.put("value.serializer", (String)ctx.getBean("valueserializer"));
	    
	    RecordMetadata result;
	    KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
	    
	    String topicname=(String)ctx.getBean("filetranstopic");
	    String message=filetransinfo.get("message"); 
	    
	    logger.info("fileTransReq filetranstopic : " + topicname);;
	    logger.info("fileTransReq message : " + message);;
        try{
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topicname, message);
            
            Future<RecordMetadata> recordMetadata = producer.send(producerRecord);
            
            result = recordMetadata.get();
            
        }catch (Exception ex){        	logger.info("fileTransReq error : " + ex);;
            throw new Exception("fileTransReq error : " + ex);
        }        // flush data
 		producer.flush();

 		// flush and close producer
 		producer.close(); 
	    
	    return result;
	}
	
	/*
	public void deleteTopic(String topicname) throws Exception {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", bootstrapServer);
	    properties.put("connections.max.idle.ms", connectiontimeout);
	    properties.put("request.timeout.ms", sessiontimeout);

	    AdminClient client = AdminClient.create(properties);
	    
	    client.deleteTopics(Collections.singleton(topicname)).all().get(DELETE_TIMEOUT_SECONDS, TimeUnit.SECONDS);
	    
	    //result.all().get();
	    
	    client.close();
	    return ;
	}*/
	
	
}
