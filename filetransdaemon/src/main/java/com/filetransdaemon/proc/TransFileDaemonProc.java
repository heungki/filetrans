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
    	
    	String proc_filepath; // 파일사이즈 체크용 진행중인 파일패스
    	
		String enc_suffix = "_enc"; // 암호화 파일 접미사
		String dec_suffix = "_dec"; // 복호화 파일 접미사
		String trans_suffix = "_tmp"; // 전송 임시 파일 접미사
		
    	// 송수신 데몬 구분
    	if(transFileModel.getDaemon_dc().equals("S")) {
    		proc_filepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file()); 
    		
    		// 파일 존재 확인
    		if(!fileCheck.chkFileExists(proc_filepath)){
    			logger.info("TransFileDaemonProc error : File Not Found");
    			transFileModel.setProc_code("ED001");
    		}
    		
    		// 파일 사이즈 확인    		
    		String filesize = fileCheck.getFileSize(proc_filepath);
    		if(!transFileModel.getFile_size().equals(filesize)){
    			logger.info("TransFileDaemonProc error : File Size Error(Req File -> " + transFileModel.getFile_size() + " Check File -> " + filesize + ")");
    			transFileModel.setProc_code("ED002");
    		}
    		    		
    		// 파일 암호화    		
    		// 암호화 여부 확인
    		if(transFileModel.getEnc_yn().equals("Y")){
    			
    			// 암호화 수행
    			if(!transFileDaemonService.fileEncrypt(transFileModel, enc_suffix)){
    				logger.info("TransFileDaemonProc error : Encrypt Error");
    				transFileModel.setProc_code("ED010");
    			}    		
    			
    			// 암호화 파일명으로 소스파일 수정
    			transFileModel.setSrc_file(transFileModel.getSrc_file()+enc_suffix);
    			
    			// 암호화 파일 사이즈 확인
        		proc_filepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file()); 
        		transFileModel.setEncfile_size(
        				fileCheck.getFileSize(proc_filepath));
        		
        		trans_suffix = enc_suffix;
    		}
    		
        	// 파일 전송
    		if(!transFileDaemonService.fileTrans(transFileModel, trans_suffix)) {
    			logger.info("TransFileDaemonProc error : File Transfer Error");
				transFileModel.setProc_code("ED020");
    		}
    		
    		// 전송결과 전문생성    		
        	        	  	
        	// 소스 결과-Topic 전송
        	Gson messageGson = new Gson();
    		
        	String messageJson = messageGson.toJson(transFileModel);
        	String topicName = "srcrslt-topic";
        	try {
				kafkaProducerService.sendMessage(topicName, messageJson);
			} catch (Exception e) {
				logger.error("TransFileDaemonProc error : Srctgt Topic send Error" + e);
			}
    	}else if(transFileModel.getDaemon_dc().equals("R")) {
    		proc_filepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file());
    		
    		if(transFileModel.getEnc_yn().equals("Y")) {
				proc_filepath += enc_suffix;
			}
    		// 파일 존재 확인
    		if(!fileCheck.chkFileExists(proc_filepath)){
    			
    			logger.info("TransFileDaemonProc error : File Not Found " + proc_filepath);
    			transFileModel.setProc_code("ED001");
    		}
    		
    			
    		String filesize = fileCheck.getFileSize(proc_filepath);
    		    		
    		// 파일 복호화	
    		// 암호화 여부 확인
    		if(transFileModel.getEnc_yn().equals("Y")){
    			
    			// 암호화 파일 사이즈 확인    	
    			if(!transFileModel.getEncfile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getEncfile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        		}
    			
    			// 복호화 수행
    			if(!transFileDaemonService.fileDecrypt(transFileModel, enc_suffix, dec_suffix)){
    				logger.info("TransFileDaemonProc error : Decrypt Error");
    				transFileModel.setProc_code("ED010");
    			}
    			
    			proc_filepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file() + dec_suffix); 
    			// 복호화 파일과 원본사이즈 확인   
        		filesize = fileCheck.getFileSize(proc_filepath);
        		if(!transFileModel.getFile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getFile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        		}
        		
    		}else {
    			// 파일 사이즈 확인    	
    			if(!transFileModel.getFile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getFile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        		}
    		}
    		
    		// 타겟 결과 전문 조립
    		transFileModel.setProc_code("SS001");
    		
        	// 타켓 결과-Topic 전송
        	Gson messageGson = new Gson();
    		
        	String messageJson = messageGson.toJson(transFileModel);
        	String topicName = "tgtrslt-topic";
        	try {
				kafkaProducerService.sendMessage(topicName, messageJson);
			} catch (Exception e) {
				logger.error("TransFileDaemonProc error : 타켓 결과 Topic 전송 에러" + e);
			}
    	}
		
    	    	
	}

}
