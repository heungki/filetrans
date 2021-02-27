package com.filetransdaemon.service;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.filetransdaemon.model.TransFileModel;
import com.filetransdaemon.util.FileAES256;
import com.filetransdaemon.util.FileTransSFTP;

public class TransFileDaemonService {
	private static Logger logger = LoggerFactory.getLogger(TransFileDaemonService.class);
	
	// ���� ��ȣȭ
	public boolean fileEncrypt(TransFileModel transFileModel, String suffix) {
		
		String filepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file());
		
		// ��ȣȭ ���ϸ�		
		String encfilepath = filepath + suffix;		
        logger.info("Start encrypt file");        
		boolean result = true;
		
		// AES ��ȣȭ
		if(transFileModel.getEnc_type().equals("AES")) {
			FileAES256 util = new FileAES256();
			try {
				result = util.encryptFile(filepath, encfilepath);
				logger.info("encrypt file result: " + String.valueOf(result));
			} catch (Exception e) {
				logger.error("encrypt file error: " + e);
				return false;
			}
		}
		// ... ��ȣȭ
		else if(transFileModel.getEnc_type().equals("...")){
			
		}
		
		return result;
	}
	
	// ���� ��ȣȭ
	public boolean fileDecrypt(TransFileModel transFileModel, String enc_suffix, String dec_suffix) {
		FileAES256 util = new FileAES256();
		// ��ȣȭ ���ϸ�
		String filepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file() + enc_suffix);
		
		// ��ȣȭ ���ϸ�	
		String decfilepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file() + dec_suffix);
		
        logger.info("Start decrypt file " + filepath + " -> " + decfilepath);        
		boolean result = true;
		try {
			result = util.decryptFile(filepath, decfilepath);
			logger.info("decrypt file result: " + String.valueOf(result));
		} catch (Exception e) {
			logger.error("decrypt file error: " + e);
			return false;
		}
		
		return result;
	}
	
	// ���� ����
	public boolean fileTrans(TransFileModel transFileModel, String suffix) {
		
		logger.info("Start Trans file");		
		
		String srcfilepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file());
		String dstfilepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file() + suffix);
		
		boolean result = true;
		
		logger.info("fileTrans -> " + transFileModel.getServer_ip() + " " + transFileModel.getServer_port() + " " +
				transFileModel.getFtp_id() + " " + srcfilepath + " " + dstfilepath);
		// SFTP Ÿ�� ����
		if(transFileModel.getTrans_type().equals("S")) {
			FileTransSFTP fileTrans = new FileTransSFTP(transFileModel.getServer_ip(), 
					Integer.parseInt(transFileModel.getServer_port()),
					transFileModel.getFtp_id(), transFileModel.getPassword(), 10000, 15000);
			result = fileTrans.uploadFile(srcfilepath, transFileModel.getTgt_dir(), transFileModel.getTgt_file() + suffix);
			logger.info("Trans result: " + String.valueOf(result));
		}
		// ... Ÿ�� ����
		else if(transFileModel.getTrans_type().equals("...")){
			
		}

		return result;
	}
	
	

}
