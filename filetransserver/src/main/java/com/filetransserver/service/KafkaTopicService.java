package com.filetransserver.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteConsumerGroupsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaTopicService {
	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
	@Value("${spring.kafka.sessiontimeout}")
    private String sessiontimeout;
	@Value("${spring.kafka.connectiontimeout}")
    private String connectiontimeout;
	
	public CreateTopicsResult createTopic(String topicname) throws Exception {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", bootstrapServer);
	    properties.put("connections.max.idle.ms", connectiontimeout);
	    properties.put("request.timeout.ms", sessiontimeout);
	    
	    AdminClient client = AdminClient.create(properties);
	    CreateTopicsResult    result = client.createTopics(Arrays.asList(
	                new NewTopic(topicname, 1, (short) 1)
	        ));
	    
	    //result.all().get();
	    	    
	    return result;
	}
	
	public void deleteTopic(String topicname) throws Exception {
		Properties properties = new Properties();
		properties.put("bootstrap.servers", bootstrapServer);
	    properties.put("connections.max.idle.ms", connectiontimeout);
	    properties.put("request.timeout.ms", sessiontimeout);

	    AdminClient client = AdminClient.create(properties);
	    
	    client.deleteTopics(Collections.singleton(topicname)).all().get(30, TimeUnit.SECONDS);
	    
	    //result.all().get();
	    
	    return ;
	}
	
	
}
