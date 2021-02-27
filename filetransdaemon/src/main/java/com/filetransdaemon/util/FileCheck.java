package com.filetransdaemon.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.filetransdaemon.proc.TransFileDaemonProc;

public class FileCheck {
	private static Logger logger = LoggerFactory.getLogger(FileCheck.class);
	
	// 파일 사이즈 확인
	public boolean chkFileExists(String filepath) {
		boolean result = true;
				
		File tmp = new File(filepath);
		
		if(!tmp.exists()) {
			return false;
		}
		
		return result;
	}
	
	// 파일 사이즈 확인
	public String getFileSize(String filepath) {
		long filesize = 0;
		
		File tmp = new File(filepath);
		
		if(tmp.exists()) {
			filesize = tmp.length();
		}
		
		logger.info("File size: " + filesize);
		
		return Long.toString(filesize);
	}

}
