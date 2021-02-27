package com.filetransserver.proc;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.filetransserver.model.Server_Info;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransLogRepository;

public class TransLogProc {
	private static Logger logger = LoggerFactory.getLogger(TransLogProc.class);
		
	private final TransLogRepository transLogRepository;

    @Autowired
    public TransLogProc(TransLogRepository transLogRepository) {
        this.transLogRepository = transLogRepository;
    }
    
    // 로그 적재
	/*
	 * public Trans_Info getTransInfo(Trans_Info trans_Info) {
	 * 
	 * String trans_id = trans_Info.getTrans_id(); logger.info("trans_id -> " +
	 * trans_id); Optional<Trans_Info> entity =
	 * transInfoRepository.findById(trans_id);
	 * 
	 * Trans_Info result = new Trans_Info();
	 * 
	 * try { result= entity.get(); }catch(Exception e) {
	 * logger.error("getTransInfo error " + e); } logger.info("Trans_nm -> " +
	 * result.getTrans_nm()); return result; }
	 */ 

}
