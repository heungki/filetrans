package com.filetransdaemon.proc;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import com.filetransdaemon.model.TransFileModel;
import com.filetransdaemon.service.KafkaProducerService;
import com.filetransdaemon.service.TransFileDaemonService;
import com.filetransdaemon.util.FileCheck;
import com.filetransdaemon.util.FileTransUtil;
import com.google.gson.Gson;

public class TransFileDaemonProc {
	private static Logger logger = LoggerFactory.getLogger(TransFileDaemonProc.class);
	
	private KafkaTemplate kafkaTemplate;

    @Autowired
    public TransFileDaemonProc(KafkaTemplate kafkaTemplate
    			) {
        this.kafkaTemplate = kafkaTemplate;
    }
	
	public void TransFileDaemonProc(TransFileModel transFileModel) {	
    	KafkaProducerService kafkaProducerService = new KafkaProducerService(kafkaTemplate);
    	
    	TransFileDaemonService transFileDaemonService = new TransFileDaemonService();
    	FileCheck fileCheck = new FileCheck();
    	FileTransUtil fileTransUtil = new FileTransUtil();
    	
    	String proc_filepath; // 파일사이즈 체크용 진행중인 파일패스
    	
		String enc_suffix = "_enc"; // 암호화 파일 접미사
		String dec_suffix = "_dec"; // 복호화 파일 접미사
		String trans_suffix = "_tmp"; // 전송 임시 파일 접미사
		transFileModel.setProc_code("SS001");		
		
    	// 송수신 데몬 구분
    	if(transFileModel.getDaemon_dc().equals("S")) {
        	String topicName = "srcrslt-topic";
    		proc_filepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file()); 
    		
    		
    		// 파일 존재 확인
    		if(!fileCheck.chkFileExists(proc_filepath)){
    			logger.info("TransFileDaemonProc error : File Not Found");
    			transFileModel.setProc_code("ED001");
    			TransResultProc(topicName,transFileModel);
    			return;
    		}
    		
    		// 파일 사이즈 확인    		
    		String filesize = fileCheck.getFileSize(proc_filepath);
    		if(!transFileModel.getFile_size().equals(filesize)){
    			logger.info("TransFileDaemonProc error : File Size Error(Req File -> " + transFileModel.getFile_size() + " Check File -> " + filesize + ")");
    			transFileModel.setProc_code("ED002");
    			TransResultProc(topicName,transFileModel);
    			return;
    		}
    		    		
    		// 파일 암호화    		
    		// 암호화 여부 확인
    		if(transFileModel.getEnc_yn().equals("Y")){
    			
    			// 암호화 수행
    			if(!transFileDaemonService.fileEncrypt(transFileModel, enc_suffix)){
    				logger.info("TransFileDaemonProc error : Encrypt Error");
    				transFileModel.setProc_code("ED010");
    				TransResultProc(topicName,transFileModel);
    				return;
    			}    		
    			    			    			
    			// 암호화 파일 사이즈 확인
        		proc_filepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file()+enc_suffix); 
        		transFileModel.setEncfile_size(
        				fileCheck.getFileSize(proc_filepath));
        		
    		}
    		
    		String tmp_suffix = trans_suffix;
    		if(transFileModel.getEnc_yn().equals("Y")) {
    			tmp_suffix = enc_suffix;
    		}
    		
        	// 파일 전송
    		if(!transFileDaemonService.fileTrans(transFileModel, enc_suffix, tmp_suffix)) {
    			logger.info("TransFileDaemonProc error : File Transfer Error");
				transFileModel.setProc_code("ED020");
				TransResultProc(topicName,transFileModel);
				return;
    		}
    		
    		// 전송결과 전문생성    
        	try {
        		// 클라이언트시간
        		transFileModel.setClient_time(fileTransUtil.getCurrentTime());
    		} catch (Exception e) {
    			logger.error("TransFileProc  " + e);
    		}
        	        	  	
        	// 소스 결과-Topic 전송
        	Gson messageGson = new Gson();
    		
        	String messageJson = messageGson.toJson(transFileModel);
        	try {
				kafkaProducerService.sendMessage(topicName, messageJson);
			} catch (Exception e) {
				logger.error("TransFileDaemonProc error : Srctgt Topic send Error" + e);
				return;
			}
    	}else if(transFileModel.getDaemon_dc().equals("R")) {
    		String topicName = "tgtrslt-topic";
    		
    		proc_filepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file());
    		
    		if(transFileModel.getEnc_yn().equals("Y")) {
				proc_filepath += enc_suffix;
			}else {
				proc_filepath += trans_suffix;
			}
    		// 파일 존재 확인
    		if(!fileCheck.chkFileExists(proc_filepath)){
    			
    			logger.info("TransFileDaemonProc error : File Not Found " + proc_filepath);
    			transFileModel.setProc_code("ED001");
    			TransResultProc(topicName,transFileModel);
    			return;
    		}
    		
    			
    		String filesize = fileCheck.getFileSize(proc_filepath);
    		    		
    		// 파일 복호화	
    		// 암호화 여부 확인
    		if(transFileModel.getEnc_yn().equals("Y")){
    			
    			// 암호화 파일 사이즈 확인    	
    			if(!transFileModel.getEncfile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getEncfile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        			TransResultProc(topicName,transFileModel);
        			return;
        		}
    			
    			// 복호화 수행
    			if(!transFileDaemonService.fileDecrypt(transFileModel, enc_suffix, dec_suffix)){
    				logger.info("TransFileDaemonProc error : Decrypt Error");
    				transFileModel.setProc_code("ED010");
    				TransResultProc(topicName,transFileModel);
    				return;
    			}
    			
    			proc_filepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file() + dec_suffix); 
    			// 복호화 파일과 원본사이즈 확인   
        		filesize = fileCheck.getFileSize(proc_filepath);
        		if(!transFileModel.getFile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getFile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        			TransResultProc(topicName,transFileModel);
        			return;
        		}
        		
        		
    		}else {
    			// 파일 사이즈 확인    	
    			if(!transFileModel.getFile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getFile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        			TransResultProc(topicName,transFileModel);
        			return;
        		}
    		}
    		
    		
    		String tmp_suffix = trans_suffix;
    		if(transFileModel.getEnc_yn().equals("Y")) {
    			tmp_suffix = dec_suffix;
    		}
    		
    		// 임시(또는 복호화)파일명을 원본파일명으로 변환
    		fileTransUtil.renameFile(transFileModel, tmp_suffix);
    		
    		// 타겟 결과 전문 조립
    		// 전송결과 전문생성    
        	try {
        		// 클라이언트시간
        		transFileModel.setClient_time(fileTransUtil.getCurrentTime());
    		} catch (Exception e) {
    			logger.error("TransFileProc  " + e);
    		}      
        	
        	// 타켓 결과-Topic 전송
        	TransResultProc(topicName,transFileModel);
        	
        	
    	}
    	    	
	}
	
	public void TransResultProc(String topicName, TransFileModel transFileModel) {
		KafkaProducerService kafkaProducerService = new KafkaProducerService(kafkaTemplate);
		
		
    	Gson tgtmessageGson = new Gson();
		
    	String tgtmessageJson = tgtmessageGson.toJson(transFileModel);
    	
    	try {
			kafkaProducerService.sendMessage(topicName, tgtmessageJson);
		} catch (Exception e) {
		}   
		
	}

}
