package com.filetransserver.service;

import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.filetransserver.model.TransFileModel;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.proc.TransFileProc;
import com.filetransserver.proc.TransResultProc;
import com.filetransserver.proc.TransTargetProc;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.google.gson.Gson;

@Component
public class KafkaConsumerService {
	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	private ServerInfoRepository serverInfoRepository;
	private TransInfoRepository transInfoRepository;
	private KafkaTemplate kafkaTemplate;
	
	@Autowired
    public KafkaConsumerService(ServerInfoRepository serverInfoRepository,
    			TransInfoRepository transInfoRepository,
    			KafkaTemplate kafkaTemplate) {
        this.serverInfoRepository = serverInfoRepository;
        this.transInfoRepository = transInfoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
	// ���� ���� Topic Listen
    @KafkaListener(topics = "filetrans-topic")
    public void listenGroupFileTrans(String message) {
    	logger.info("received message filetrans -> " + message);
    	
    	TransFileProc transFileService = new TransFileProc(serverInfoRepository, transInfoRepository, kafkaTemplate);
    	boolean isJson = true;
    	try
    	{
    		JSONParser parser = new JSONParser(message);
    	
	    	parser.parse();
	    	
    	}catch(Exception e){
    		isJson = false;
    	}
    	
    	if(isJson) {
    		// Json���� ��ȯ ��  FileTransModel�� ��ȯ
	    	Gson messageJson = new Gson();
	    	
	    	TransFileModel fileTransModel = messageJson.fromJson(message, TransFileModel.class);
	    	
	    	// ���� ���� ���μ��� ����
	    	transFileService.TransFileProc(fileTransModel);
    	}
    }  
    
    // �ҽ� ��� Topic Listen
    @KafkaListener(topics = "srcrslt-topic")
    public void listenGroupSrcRslt(String message) {
    	logger.info("received message srcrslt -> " + message);
    	
    	TransTargetProc transTargetProc = new TransTargetProc(serverInfoRepository, transInfoRepository, kafkaTemplate);
    	boolean isJson = true;
    	try
    	{
    		JSONParser parser = new JSONParser(message);
    	
	    	parser.parse();
	    	
    	}catch(Exception e){
    		isJson = false;
    	}
    	
    	if(isJson) {
    		// Json���� ��ȯ ��  FileTransModel�� ��ȯ
	    	Gson messageJson = new Gson();
	    	
	    	TransFileModel fileTransModel = messageJson.fromJson(message, TransFileModel.class);
	    	
	    	// Ÿ�� ���μ��� ����
	    	transTargetProc.TransTargetProc(fileTransModel);
    	}
    }  
    
    // Ÿ�� ��� Topic Listen
    @KafkaListener(topics = "tgtrslt-topic")
    public void listenGroupTgtRslt(String message) {
    	logger.info("received message tgtrslt -> " + message);
    	
    	TransResultProc transResultProc = new TransResultProc(serverInfoRepository, transInfoRepository, kafkaTemplate);
    	boolean isJson = true;
    	try
    	{
    		JSONParser parser = new JSONParser(message);
    	
	    	parser.parse();
	    	
    	}catch(Exception e){
    		isJson = false;
    	}
    	
    	if(isJson) {
    		// Json���� ��ȯ ��  FileTransModel�� ��ȯ
	    	Gson messageJson = new Gson();
	    	
	    	TransFileModel fileTransModel = messageJson.fromJson(message, TransFileModel.class);
	    	
	    	// ��� ���μ��� ����
	    	transResultProc.TransResultProc(fileTransModel);
    	}
    }  
}