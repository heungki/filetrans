package com.filetransserver.proc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.filetransserver.model.Server_Info;
import com.filetransserver.model.TransFileMapper;
import com.filetransserver.model.TransFileModel;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.model.Trans_Log;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.filetransserver.service.KafkaProducerService;
import com.filetransserver.service.ServerInfoService;
import com.filetransserver.service.TransInfoService;
import com.filetransserver.util.FileTransUtil;
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
    	TransErrorProc transErrorProc = new TransErrorProc(kafkaTemplate);
    	FileTransUtil fileTransUtil = new FileTransUtil();
    	
    	// START Log
    	// 로그 정보 매핑
    	Trans_Log trans_log = new Trans_Log();
    	transFileMapper.transLogMapper().map(transFileModel, trans_log);
    	logger.info("transLogMapper -> " + trans_log.toString());
    	try {
    		// 서버시간
			trans_log.setServer_time(fileTransUtil.getCurrentTime());
			trans_log.setTrans_date(trans_log.getServer_time().substring(0, 8));
		} catch (Exception e) {
			logger.error("TransFileProc  " + e);
		}
    	trans_log.setStatus("START");
    	
    	// 로그 Topic 전송
    	Gson messageGson = new Gson();
    	String messageJson = messageGson.toJson(trans_log);
    	String logTopicName = "log1-topic";
    	try {
			kafkaProducerService.sendMessage(logTopicName, messageJson);
		} catch (Exception e) {
			logger.error("TransFileProc -> " + e);
		}
    	
		// 전송 정보 조회
    	Trans_Info trans_info = new Trans_Info();
    	trans_info.setTrans_id(transFileModel.getTrans_id());       	
    	try {
    		trans_info = transInfoService.getTransInfo(trans_info);
		} catch (Exception e) {
			logger.error("TransFileProc -> " + e);
			transFileModel.setProc_code("ES001");
			transErrorProc.TransErrorProc(transFileModel);
			return;
		}
    	
    	// 전송 정보 매핑
    	transFileMapper.transInfoMapper().map(trans_info, transFileModel);
    	logger.info("transInfoMapper -> " + transFileModel.toString());
    	
    	// 타켓 서버 정보 조회 
    	Server_Info server_info = new Server_Info();
    	server_info.setServer_id(transFileModel.getTgt_server_id());    	
    	try {
    		server_info = serverInfoService.getServerInfo(server_info);
		} catch (Exception e) {
			logger.error("TransFileProc -> " + e);
			transFileModel.setProc_code("ES002");
			transErrorProc.TransErrorProc(transFileModel);
			return;
		}
    	
    	// 타켓 정보 매핑
    	transFileMapper.serverInfoMapper().map(server_info, transFileModel);
    	logger.info("serverInfoMapper -> " + transFileModel.toString());
    	
    	// 데몬 정보 세팅
    	transFileModel.setDaemon_dc("S"); // 송신 데몬
    	
    	// 소스-Topic 전송
    	Gson srcmessageGson = new Gson();
    	
    	String srcmessageJson = srcmessageGson.toJson(transFileModel);
    	String srctopicName = transFileModel.getSrc_server_id()+"-topic";
    	try {
			kafkaProducerService.sendMessage(srctopicName, srcmessageJson);
		} catch (Exception e) {
			logger.error("TransFileProc -> " + e);
			transFileModel.setProc_code("ES010");
			transErrorProc.TransErrorProc(transFileModel);
			return;
		}
    	
    	// SEND Log    	
    	// 로그 정보 매핑
    	try {
    		// 서버시간
			trans_log.setServer_time(fileTransUtil.getCurrentTime());
		} catch (Exception e) {
			logger.error("TransFileProc  " + e);
		}
    	trans_log.setStatus("SEND");  
    	
    	messageGson = new Gson();    	
    	messageJson = messageGson.toJson(trans_log);
    	// 로그 Topic 전송    	
    	try {
			kafkaProducerService.sendMessage(logTopicName, messageJson);
		} catch (Exception e) {
			logger.error("TransFileProc -> " + e);			
		}
    	
	}

}
