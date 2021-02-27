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
	// 파일 전송 Topic Listen
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
    		// Json으로 변환 뒤  FileTransModel로 변환
	    	Gson messageJson = new Gson();
	    	
	    	TransFileModel fileTransModel = messageJson.fromJson(message, TransFileModel.class);
	    	
	    	// 파일 전송 프로세스 시작
	    	transFileService.TransFileProc(fileTransModel);
    	}
    }  
    
    // 소스 결과 Topic Listen
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
    		// Json으로 변환 뒤  FileTransModel로 변환
	    	Gson messageJson = new Gson();
	    	
	    	TransFileModel fileTransModel = messageJson.fromJson(message, TransFileModel.class);
	    	
	    	// 타켓 프로세스 시작
	    	transTargetProc.TransTargetProc(fileTransModel);
    	}
    }  
    
    // 타켓 결과 Topic Listen
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
    		// Json으로 변환 뒤  FileTransModel로 변환
	    	Gson messageJson = new Gson();
	    	
	    	TransFileModel fileTransModel = messageJson.fromJson(message, TransFileModel.class);
	    	
	    	// 결과 프로세스 시작
	    	transResultProc.TransResultProc(fileTransModel);
    	}
    }  
}