package com.filetransserver.proc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.filetransserver.model.Server_Info;
import com.filetransserver.model.TransFileModel;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.filetransserver.service.KafkaProducerService;
import com.google.gson.Gson;

public class TransTargetProc {
	private static Logger logger = LoggerFactory.getLogger(TransTargetProc.class);
	
	private ServerInfoRepository serverInfoRepository;
	private TransInfoRepository transInfoRepository;
	private KafkaTemplate kafkaTemplate;

    @Autowired
    public TransTargetProc(ServerInfoRepository serverInfoRepository,
    			TransInfoRepository transInfoRepository
    			,KafkaTemplate kafkaTemplate
    			) {
        this.serverInfoRepository = serverInfoRepository;
        this.transInfoRepository = transInfoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
	
	public void TransTargetProc(TransFileModel transFileModel) {
		KafkaProducerService kafkaProducerService = new KafkaProducerService(kafkaTemplate);
		
    	// RECV Log-Topic 전송
    	
		// 타켓 정보 조회

    	// 타켓-Topic 전송
    	// 데몬 정보 세팅
    	transFileModel.setDaemon_dc("R"); // 수신 데몬
    	// 불필요 항목 삭제
    	transFileModel.setServer_ip("");
		transFileModel.setServer_port("");
		transFileModel.setFtp_id("");
		transFileModel.setPassword("");
		
    	
    	// 소스-Topic 전송
    	Gson messageGson = new Gson();
    	
    	String messageJson = messageGson.toJson(transFileModel);
    	String topicName = transFileModel.getTgt_server_id()+"-topic";
    	try {
			kafkaProducerService.sendMessage(topicName, messageJson);
		} catch (Exception e) {
			logger.info("TransTargetProc -> " + e);
		}   	
    	
    	// Target Log-Topic 전송
    	
    	
	}

}
