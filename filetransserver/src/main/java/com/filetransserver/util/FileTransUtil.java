package com.filetransserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileTransUtil {
	private Logger logger = LoggerFactory.getLogger(FileTransUtil.class);

	// 현재 시간 조회
	public String getCurrentTime() throws Exception {
		
		String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String current_time = simpleDateFormat.format(new Date());
		
		return current_time;
	}

}
