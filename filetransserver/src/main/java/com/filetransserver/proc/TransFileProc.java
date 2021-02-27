package com.filetransserver.proc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.filetransserver.model.Server_Info;
import com.filetransserver.model.TransFileMapper;
import com.filetransserver.model.TransFileModel;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.filetransserver.service.KafkaProducerService;
import com.filetransserver.service.ServerInfoService;
import com.filetransserver.service.TransInfoService;
import com.google.gson.Gson;

public class TransFileProc {
	private static Logger logger = LoggerFactory.getLogger(TransFileProc.class);
	
	private ServerInfoRepository serverInfoRepository;
	private TransInfoRepository transInfoRepository;
	private KafkaTemplate kafkaTemplate;

    @Autowired
    public TransFileProc(ServerInfoRepository serverInfoRepository,
    			TransInfoRepository transInfoRepository
    			,KafkaTemplate kafkaTemplate
    			) {
        this.serverInfoRepository = serverInfoRepository;
        this.transInfoRepository = transInfoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
	
	public void TransFileProc(TransFileModel transFileModel) {	
		ServerInfoService serverInfoService = new ServerInfoService(serverInfoRepository);
    	TransInfoService transInfoService = new TransInfoService(transInfoRepository);
    	KafkaProducerService kafkaProducerService = new KafkaProducerService(kafkaTemplate);
    	TransFileMapper transFileMapper = new TransFileMapper();
		
    	// START Log-Topic 전송
    	
		// 전송 정보 조회
    	Trans_Info trans_info = new Trans_Info();
    	trans_info.setTrans_id(transFileModel.getTrans_id());    	
    	trans_info = transInfoService.getTransInfo(trans_info);
    	
    	// 전송 정보 매핑
    	transFileMapper.transInfoMapper().map(trans_info, transFileModel);
    	logger.info("transInfoMapper -> " + transFileModel.toString());
    	
    	// 타켓 서버 정보 조회 
    	Server_Info server_info = new Server_Info();
    	server_info.setServer_id(transFileModel.getTgt_server_id());
    	server_info = serverInfoService.getServerInfo(server_info);
    	
    	// 타켓 정보 매핑
    	transFileMapper.serverInfoMapper().map(server_info, transFileModel);
    	logger.info("serverInfoMapper -> " + transFileModel.toString());
    	
    	// 데몬 정보 세팅
    	transFileModel.setDaemon_dc("S"); // 송신 데몬
    	
    	// 소스-Topic 전송
    	Gson messageGson = new Gson();
    	
    	String messageJson = messageGson.toJson(transFileModel);
    	String topicName = transFileModel.getSrc_server_id()+"-topic";
    	try {
			kafkaProducerService.sendMessage(topicName, messageJson);
		} catch (Exception e) {
			logger.info("TransFileProc -> " + e);
		}
    	
    	// SEND Log-Topic 전송
    	
    	
	}

}
