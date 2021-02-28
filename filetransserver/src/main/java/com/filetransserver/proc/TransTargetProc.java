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
import com.filetransserver.util.FileTransUtil;
import com.google.gson.Gson;

public class TransTargetProc {
	private static Logger logger = LoggerFactory.getLogger(TransTargetProc.class);
	
	private KafkaTemplate kafkaTemplate;

    @Autowired
    public TransTargetProc( KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
	
	public void TransTargetProc(TransFileModel transFileModel) {
		KafkaProducerService kafkaProducerService = new KafkaProducerService(kafkaTemplate);
		
		TransFileMapper transFileMapper = new TransFileMapper();
    	FileTransUtil fileTransUtil = new FileTransUtil();
    	TransErrorProc transErrorProc = new TransErrorProc(kafkaTemplate);
    	
    	// �ҽ� ���� ������ �ӽ� Topic�� ��������    	
    	if(!transFileModel.getProc_code().equals("SS001")) {
			transErrorProc.TransErrorProc(transFileModel);
			return;
    	}
    			
    	// RECV Log
    	// �α� ���� ����
    	Trans_Log trans_log = new Trans_Log();
    	transFileMapper.transLogMapper().map(transFileModel, trans_log);
    	logger.info("transLogMapper -> " + trans_log.toString());
    	try {
    		// �����ð�
			trans_log.setServer_time(fileTransUtil.getCurrentTime());
			trans_log.setTrans_date(trans_log.getServer_time().substring(0, 8));
		} catch (Exception e) {
			logger.error("TransTargetProc  " + e);
		}
    	trans_log.setStatus("RECV");
    	
    	// �α� Topic ����
    	Gson messageGson = new Gson();    	
    	String messageJson = messageGson.toJson(trans_log);
    	String logTopicName = "log1-topic";
    	try {
			kafkaProducerService.sendMessage(logTopicName, messageJson);
		} catch (Exception e) {
			logger.error("TransTargetProc -> " + e);
		}
    	
		// Ÿ�� ���� ��ȸ

    	// Ÿ��-Topic ����
    	// ���� ���� ����
    	transFileModel.setDaemon_dc("R"); // ���� ����
    	// ���ʿ� �׸� ����
    	transFileModel.setServer_ip("");
		transFileModel.setServer_port("");
		transFileModel.setFtp_id("");
		transFileModel.setPassword("");
		
    	
    	// Ÿ��-Topic ����
    	Gson tgtmessageGson = new Gson();
    	
    	String tgtmessageJson = tgtmessageGson.toJson(transFileModel);
    	String topicName = transFileModel.getTgt_server_id()+"-topic";
    	try {
			kafkaProducerService.sendMessage(topicName, tgtmessageJson);
		} catch (Exception e) {
			logger.error("TransTargetProc -> " + e);
			transFileModel.setProc_code("ES011");
			transErrorProc.TransErrorProc(transFileModel);
		}   	
    	
    	// Target Log	
    	// �α� ���� ����
    	try {
    		// �����ð�
			trans_log.setServer_time(fileTransUtil.getCurrentTime());
		} catch (Exception e) {
			logger.error("TransTargetProc  " + e);
		}
    	trans_log.setStatus("TARGET");
    	    	
    	messageGson = new Gson();    	
    	messageJson = messageGson.toJson(trans_log);    	
    	// �α� Topic ����    	
    	try {
			kafkaProducerService.sendMessage(logTopicName, messageJson);
		} catch (Exception e) {
			logger.error("TransTargetProc -> " + e);
		}
    	
	}

}
