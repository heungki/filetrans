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
    	
    	String proc_filepath; // ���ϻ����� üũ�� �������� �����н�
    	
		String enc_suffix = "_enc"; // ��ȣȭ ���� ���̻�
		String dec_suffix = "_dec"; // ��ȣȭ ���� ���̻�
		String trans_suffix = "_tmp"; // ���� �ӽ� ���� ���̻�
		
    	// �ۼ��� ���� ����
    	if(transFileModel.getDaemon_dc().equals("S")) {
    		proc_filepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file()); 
    		
    		// ���� ���� Ȯ��
    		if(!fileCheck.chkFileExists(proc_filepath)){
    			logger.info("TransFileDaemonProc error : File Not Found");
    			transFileModel.setProc_code("ED001");
    		}
    		
    		// ���� ������ Ȯ��    		
    		String filesize = fileCheck.getFileSize(proc_filepath);
    		if(!transFileModel.getFile_size().equals(filesize)){
    			logger.info("TransFileDaemonProc error : File Size Error(Req File -> " + transFileModel.getFile_size() + " Check File -> " + filesize + ")");
    			transFileModel.setProc_code("ED002");
    		}
    		    		
    		// ���� ��ȣȭ    		
    		// ��ȣȭ ���� Ȯ��
    		if(transFileModel.getEnc_yn().equals("Y")){
    			
    			// ��ȣȭ ����
    			if(!transFileDaemonService.fileEncrypt(transFileModel, enc_suffix)){
    				logger.info("TransFileDaemonProc error : Encrypt Error");
    				transFileModel.setProc_code("ED010");
    			}    		
    			
    			// ��ȣȭ ���ϸ����� �ҽ����� ����
    			transFileModel.setSrc_file(transFileModel.getSrc_file()+enc_suffix);
    			
    			// ��ȣȭ ���� ������ Ȯ��
        		proc_filepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file()); 
        		transFileModel.setEncfile_size(
        				fileCheck.getFileSize(proc_filepath));
        		
        		trans_suffix = enc_suffix;
    		}
    		
        	// ���� ����
    		if(!transFileDaemonService.fileTrans(transFileModel, trans_suffix)) {
    			logger.info("TransFileDaemonProc error : File Transfer Error");
				transFileModel.setProc_code("ED020");
    		}
    		
    		// ���۰�� ��������    		
        	        	  	
        	// �ҽ� ���-Topic ����
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
    		// ���� ���� Ȯ��
    		if(!fileCheck.chkFileExists(proc_filepath)){
    			
    			logger.info("TransFileDaemonProc error : File Not Found " + proc_filepath);
    			transFileModel.setProc_code("ED001");
    		}
    		
    			
    		String filesize = fileCheck.getFileSize(proc_filepath);
    		    		
    		// ���� ��ȣȭ	
    		// ��ȣȭ ���� Ȯ��
    		if(transFileModel.getEnc_yn().equals("Y")){
    			
    			// ��ȣȭ ���� ������ Ȯ��    	
    			if(!transFileModel.getEncfile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getEncfile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        		}
    			
    			// ��ȣȭ ����
    			if(!transFileDaemonService.fileDecrypt(transFileModel, enc_suffix, dec_suffix)){
    				logger.info("TransFileDaemonProc error : Decrypt Error");
    				transFileModel.setProc_code("ED010");
    			}
    			
    			proc_filepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file() + dec_suffix); 
    			// ��ȣȭ ���ϰ� ���������� Ȯ��   
        		filesize = fileCheck.getFileSize(proc_filepath);
        		if(!transFileModel.getFile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getFile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        		}
        		
    		}else {
    			// ���� ������ Ȯ��    	
    			if(!transFileModel.getFile_size().equals(filesize)){
        			logger.info("TransFileDaemonProc error : File Size Error(Req file -> " + transFileModel.getFile_size() + " Check file -> " + filesize + ")");
        			transFileModel.setProc_code("ED002");
        		}
    		}
    		
    		// Ÿ�� ��� ���� ����
    		transFileModel.setProc_code("SS001");
    		
        	// Ÿ�� ���-Topic ����
        	Gson messageGson = new Gson();
    		
        	String messageJson = messageGson.toJson(transFileModel);
        	String topicName = "tgtrslt-topic";
        	try {
				kafkaProducerService.sendMessage(topicName, messageJson);
			} catch (Exception e) {
				logger.error("TransFileDaemonProc error : Ÿ�� ��� Topic ���� ����" + e);
			}
    	}
		
    	    	
	}

}
