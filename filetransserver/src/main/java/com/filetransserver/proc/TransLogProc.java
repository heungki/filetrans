package com.filetransserver.proc;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.filetransserver.model.Trans_Log;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.filetransserver.repository.TransLogRepository;
import com.filetransserver.util.FileTransUtil;

public class TransLogProc {
private static Logger logger = LoggerFactory.getLogger(TransFileProc.class);
	
	private TransLogRepository transLogRepository;
	private KafkaTemplate kafkaTemplate;
	
    @Autowired
    public TransLogProc(TransLogRepository transLogRepository,
    		KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.transLogRepository = transLogRepository;
    }
	
	public void TransLogProc(Trans_Log trans_log) {
		FileTransUtil fileTransUtil = new FileTransUtil();
    	logger.info("TransLogProc -> " + trans_log.toString());
    	
    	// ·Î±× Insert
    	transLogRepository.save(trans_log);
	}
	
}
