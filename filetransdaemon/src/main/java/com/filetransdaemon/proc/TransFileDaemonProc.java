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
    	
    	String proc_filepath; // ���ϻ����� üũ�� �������� �����н�
    	
		String enc_suffix = "_enc"; // ��ȣȭ ���� ���̻�
		String dec_suffix = "_dec"; // ��ȣȭ ���� ���̻�
		String trans_suffix = "_tmp"; // ���� �ӽ� ���� ���̻�
		transFileModel.setProc_code("SS001");		
		
    	// �ۼ��� ���� ����
    	if(transFileModel.getDaemon_dc().equals("S")) {
        	String topicName = "srcrslt-topic";
    		proc_filepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file()); 
    		
    		
    		// ���� ���� Ȯ��
    		if(!fileCheck.chkFileExists(proc_filepath)){
    			logger.info("TransFileDaemonProc error : File Not Found");
    			transFileModel.setProc_code("ED001");
    			TransResultProc(topicName,transFileModel);
    			return;
    		}
    		
    		// ���� ������ Ȯ��    		
    		String filesize = fileCheck.getFileSize(proc_filepath);
    		if(!transFileModel.getFile_size().equals(filesize)){
    			logger.info("TransFileDaemonProc error : File Size Error(Req File -> " + transFileModel.getFile_size() + " Check File -> " + filesize + ")");
    			transFileModel.setProc_code("ED002");
    			TransResultProc(topicName,transFileModel);
    			return;
    		}
    		    		
    		// ���� ��ȣȭ    		
    		// ��ȣȭ ���� Ȯ��
    		if(transFileModel.getEnc_yn().equals("Y")){
    			
    			// ��ȣȭ ����
    			if(!transFileDaemonService.fileEncrypt(transFileModel, enc_suffix)){
    				logger.info("TransFileDaemonProc error : Encrypt Error");
    				transFileModel.setProc_code("ED010");
    				TransResultProc(topicName,transFileModel);
    				return;
    			}    		
    			    			    			
    			// ��ȣȭ ���� ������ Ȯ��
        		proc_filepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file()+enc_suffix); 
        		transFileModel.setEncfile_size(
        				fileCheck.getFileSize(proc_filepath));
        		
    		}
    		
    		String tmp_suffix = trans_suffix;
    		if(transFileModel.getEnc_yn().equals("Y")) {
    			tmp_suffix = enc_suffix;
    		}
    		
        	// ���� ����
    		if(!transFileDaemonService.fileTrans(transFileModel, enc_suffix, tmp_suffix)) {
    			logger.info("TransFileDaemonProc error : File Transfer Error");
				transFileModel.setProc_code("ED020");
				TransResultProc(topicName,transFileModel);
				return;
    		}
    		
    		// ���۰�� ��������    
        	try {
        		// Ŭ���̾�Ʈ�ð�
        		transFileModel.setClient_time(fileTransUtil.getCurrentTime());
    		} catch (Exception e) {
    			logger.error("TransFileProc  " + e);
    		}
        	        	  	
        	// �ҽ� ���-Topic ����
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
    		// ���� ���� Ȯ��
    		if(!fileCheck.chkFileExists(proc_filepath)){
    			
    			logger.info("TransFileDaemonProc error : File Not Found " + proc_filepath);
    			transFileModel.setProc_code("ED001");
    			TransResultProc(topicName,transFileModel);
    			return;
    		}
    		
    			
    		String filesize = fileCheck.getFileSize(proc_filepath);
    		    		
    		// ���� ��ȣȭ	
    		// ��ȣȭ ���� Ȯ��
    		if(transFileModel.getEnc_yn().equals("Y")){
    			
    			// ��ȣȭ ���� ������ Ȯ��    	
    			if(!transFileModel.getEncfile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getEncfile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        			TransResultProc(topicName,transFileModel);
        			return;
        		}
    			
    			// ��ȣȭ ����
    			if(!transFileDaemonService.fileDecrypt(transFileModel, enc_suffix, dec_suffix)){
    				logger.info("TransFileDaemonProc error : Decrypt Error");
    				transFileModel.setProc_code("ED010");
    				TransResultProc(topicName,transFileModel);
    				return;
    			}
    			
    			proc_filepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file() + dec_suffix); 
    			// ��ȣȭ ���ϰ� ���������� Ȯ��   
        		filesize = fileCheck.getFileSize(proc_filepath);
        		if(!transFileModel.getFile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getFile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        			TransResultProc(topicName,transFileModel);
        			return;
        		}
        		
        		
    		}else {
    			// ���� ������ Ȯ��    	
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
    		
    		// �ӽ�(�Ǵ� ��ȣȭ)���ϸ��� �������ϸ����� ��ȯ
    		fileTransUtil.renameFile(transFileModel, tmp_suffix);
    		
    		// Ÿ�� ��� ���� ����
    		// ���۰�� ��������    
        	try {
        		// Ŭ���̾�Ʈ�ð�
        		transFileModel.setClient_time(fileTransUtil.getCurrentTime());
    		} catch (Exception e) {
    			logger.error("TransFileProc  " + e);
    		}      
        	
        	// Ÿ�� ���-Topic ����
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
