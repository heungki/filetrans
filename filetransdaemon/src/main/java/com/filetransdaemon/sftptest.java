package com.filetransdaemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.filetransdaemon.util.FileTrans;

@Component
public class sftptest implements CommandLineRunner {

	@Autowired
	private FileTrans fileTransferService;
	
	private Logger logger = LoggerFactory.getLogger(sftptest.class);
	
	@Override
	public void run(String... args) throws Exception {
		/*
		 * logger.info("Start download file"); boolean isDownloaded =
		 * fileTransferService.downloadFile("/home/simplesolution/readme.txt",
		 * "/readme.txt"); logger.info("Download result: " +
		 * String.valueOf(isDownloaded));
		 */
		
        logger.info("Start upload file");
		boolean isUploaded = fileTransferService.uploadFile("/sftptest_enc.txt", "/sftptestrename_enc.txt");
		logger.info("Upload result: " + String.valueOf(isUploaded));
		
		System.exit(0);
	}

}
