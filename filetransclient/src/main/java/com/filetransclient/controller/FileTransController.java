package com.filetransclient.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.filetransclient.model.TransFileModel;
import com.filetransclient.service.KafkaTopicService;
import com.filetransclient.service.KafkaConsumerService;
import com.filetransclient.service.KafkaProducerService;
import com.filetransclient.util.FileTransUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class FileTransController {	
	private Logger logger = LoggerFactory.getLogger(FileTransController.class);
	
	private KafkaTemplate kafkaTemplate;

    @Autowired
    public FileTransController(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
	

	// 파일 전송 전문 세팅
    public TransFileModel fileTransSet(TransFileModel fileTransModel) {
		
    	//전송 Key 세팅
    	FileTransUtil fileTransUtil = new FileTransUtil();
    	String trans_key;
		try {
			trans_key = fileTransModel.getClient_id()+"-"+fileTransUtil.getRandomStr(5);
			fileTransModel.setTrans_key(trans_key);
		} catch (Exception e) {
			logger.info("fileTransSet error" + e);
		}    
    	    	
    	// 클라이언트 처리시간 세팅
		String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String client_time = simpleDateFormat.format(new Date());
		fileTransModel.setClient_time(client_time);
		
		logger.info(fileTransModel.toString());
        return fileTransModel;
        
    }
    
	// topic 생성 method
    public String createTopic(TransFileModel fileTransModel) {
		KafkaTopicService cttopic= new KafkaTopicService(kafkaTemplate);
		FileTransUtil fileTransUtil = new FileTransUtil();
		String topicname = "";
		
		try {  
    		// kafka 배치 송신용 topic 생성(tmp+프로그램명+랜덤문자숫자5자리)	
    		topicname = "tmp-" + fileTransModel.getClient_id()+"-"+fileTransUtil.getRandomStr(5)+"-topic"; 
    		fileTransModel.setTemp_topic(topicname);
    		    		
    		// kafka topic 생성
    		cttopic.createTopic(fileTransModel);
    		
		} catch (Exception e) {
			logger.error("createTopic error" + e);
		}
    	
        return topicname;
    }
    
    // 파일 전송 method
    public boolean fileTransReq(TransFileModel fileTransModel) {
    	
    	KafkaProducerService kafkaProducerService = new KafkaProducerService(kafkaTemplate);
    	
    	Gson ftmgson = new Gson();
		
    	try {
    		
    		// json으로 변경
    		String ftmjson = ftmgson.toJson(fileTransModel);
    		    		    		
    		// kafka producer 메시지 전송
    		kafkaProducerService.sendMessage("filetrans-topic", ftmjson);
    		
    		
		} catch (Exception e) {
			logger.error("fileTransReq error" + e);
			return false;
		}
    	
        return true;
    }
    
    // 파일 전송 결과 수신  method
    public TransFileModel fileTransRes(String topicname) {
    	KafkaConsumerService kafkaConsumerService = new KafkaConsumerService();
    
    	TransFileModel transFileModel = kafkaConsumerService.receiveFromKafka(topicname);
    	
    	return transFileModel;
    }
    
    // 임시 파일 생성
    public boolean tempFileCreate(String src_file) {
    	
    	File file1 = new File(src_file);
    	FileOutputStream outFile = null;
    			 
		byte[] output = "ABCD12345EFGH".getBytes();
		
		if (output != null) {
			try {
				outFile = new FileOutputStream(file1);
				outFile.write(output);
				outFile.flush();
				outFile.close();
			} catch (IOException e) {
				logger.error("tempFileCreate error" + e);
				return false;
			}
		}		
    	
    	return true;
    }
    
}
