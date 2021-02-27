package com.filetransdaemon.service;

import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.filetransdaemon.model.TransFileModel;
import com.filetransdaemon.proc.TransFileDaemonProc;
import com.google.gson.Gson;

@Component
public class KafkaConsumerService {
	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	private KafkaTemplate kafkaTemplate;
	
	@Autowired
    public KafkaConsumerService(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
	
    @KafkaListener(topics = "${spring.kafka.template.listen-topic}")
    public void listenGroupFileTransDaemon(String message) {
    	logger.info("received message filetrans Daemon -> " + message);
    	
    	TransFileDaemonProc transFileService = new TransFileDaemonProc(kafkaTemplate);
    	
    	boolean isJson = true;
    	try
    	{
    		JSONParser parser = new JSONParser(message);
    	
	    	parser.parse();
	    	
    	}catch(Exception e){
    		isJson = false;
    	}
    	
    	if(isJson) {
    		// Json으로 변환 뒤  FileTransModel로 변환
	    	Gson messageJson = new Gson();
	    	
	    	TransFileModel fileTransModel = messageJson.fromJson(message, TransFileModel.class);
	    	
	    	// 데몬 프로세스 시작
	    	transFileService.TransFileDaemonProc(fileTransModel);
    	}
    }  
    
   
    
}