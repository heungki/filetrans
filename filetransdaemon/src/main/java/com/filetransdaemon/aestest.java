package com.filetransdaemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.filetransdaemon.util.FileAES256;

@Component
public class aestest implements CommandLineRunner {

	@Autowired
	private FileAES256 FileAES256;
		
	private Logger logger = LoggerFactory.getLogger(aestest.class);
	
	@Override
	public void run(String... args) throws Exception {
		/*
		 * logger.info("Start download file"); boolean isDownloaded =
		 * fileTransferService.downloadFile("/home/simplesolution/readme.txt",
		 * "/readme.txt"); logger.info("Download result: " +
		 * String.valueOf(isDownloaded));
		 */
		String encSrcFilePath = "sftptest.txt";
		String encDstFilePath = "sftptest_enc.txt";
		
		String decSrcFilePath = "sftptest_enc.txt";
		String decDstFilePath = "sftptest_dec.txt";
		
        logger.info("Start encrypt file");        
		boolean isEncrypt = FileAES256.encryptFile(encSrcFilePath, encDstFilePath);
		logger.info("encrypt file result: " + String.valueOf(isEncrypt));
		
		logger.info("Start decrypt file");        
		boolean isDecrypt = FileAES256.decryptFile(decSrcFilePath, decDstFilePath);
		logger.info("decrypt file result: " + String.valueOf(isDecrypt));
		
		System.exit(0);
	}

}

