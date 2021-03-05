package com.filetransserver.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.filetransserver.model.Trans_Log;
import com.filetransserver.repository.TransLogRepository;

public class TransLogService {
	private static Logger logger = LoggerFactory.getLogger(TransLogService.class);
		
	private final TransLogRepository transLogRepository;

    @Autowired
    public TransLogService(TransLogRepository transLogRepository) {
        this.transLogRepository = transLogRepository;
    }    

 	public List<Trans_Log> getTransLogList() {
        List<Trans_Log> entities = transLogRepository.findAll();
        return entities;
    }

}
