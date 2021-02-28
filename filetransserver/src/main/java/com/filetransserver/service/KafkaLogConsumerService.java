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
import com.filetransserver.model.Trans_Log;
import com.filetransserver.proc.TransFileProc;
import com.filetransserver.proc.TransLogProc;
import com.filetransserver.proc.TransResultProc;
import com.filetransserver.proc.TransTargetProc;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.filetransserver.repository.TransLogRepository;
import com.google.gson.Gson;

@Component
public class KafkaLogConsumerService {
	private static Logger logger = LoggerFactory.getLogger(KafkaLogConsumerService.class);
	private TransLogRepository transLogRepository;
	private KafkaTemplate kafkaTemplate;
	
	@Autowired
    public KafkaLogConsumerService(TransLogRepository transLogRepository,
    			KafkaTemplate kafkaTemplate) {
        this.transLogRepository = transLogRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
	
	// 로그 토픽 Topic Listen
	@KafkaListener(topics = "log1-topic")
    public void listenGroupTransLog(String message) {
    	logger.info("received message translog -> " + message);
    	
    	TransLogProc transLogProc = new TransLogProc(transLogRepository, kafkaTemplate);
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
	    	
	    	Trans_Log trans_log = messageJson.fromJson(message, Trans_Log.class);
	    	
	    	// 로그 적재 프로세스 시작
	    	transLogProc.TransLogProc(trans_log);
    	}
    }  
}