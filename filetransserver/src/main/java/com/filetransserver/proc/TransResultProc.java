package com.filetransserver.proc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.filetransserver.model.TransFileModel;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.filetransserver.service.KafkaProducerService;
import com.google.gson.Gson;

public class TransResultProc {
	private static Logger logger = LoggerFactory.getLogger(TransResultProc.class);
	
	private ServerInfoRepository serverInfoRepository;
	private TransInfoRepository transInfoRepository;
	private KafkaTemplate kafkaTemplate;

    @Autowired
    public TransResultProc(ServerInfoRepository serverInfoRepository,
    			TransInfoRepository transInfoRepository
    			,KafkaTemplate kafkaTemplate
    			) {
        this.serverInfoRepository = serverInfoRepository;
        this.transInfoRepository = transInfoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
	
	public void TransResultProc(TransFileModel transFileModel) {	
		KafkaProducerService kafkaProducerService = new KafkaProducerService(kafkaTemplate);
		
    	// RESULT Log-Topic 전송
    	
		// 결과 전문 생성
		transFileModel.setDaemon_dc("");
    	    	    	
    	// 임시-Topic 전송
		Gson messageGson = new Gson();
    	
    	String messageJson = messageGson.toJson(transFileModel);
    	String topicName = transFileModel.getTemp_topic();
    	try {
			kafkaProducerService.sendMessage(topicName, messageJson);
		} catch (Exception e) {
			logger.info("TransTargetProc -> " + e);
		}   
    	
    	// END Log-Topic 전송
    	
	}

}
