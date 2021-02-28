package com.filetransclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.filetransclient.service.KafkaTopicService;

public class FileTransUtil {
	private Logger logger = LoggerFactory.getLogger(KafkaTopicService.class);

	public String getRandomStr(int size) throws Exception {
		char[] tmp = new char[size];
		if(size > 0) {			
			for(int i=0; i<tmp.length; i++) {
				int div = (int) Math.floor( Math.random() * 2 );
				
				if(div == 0) { // 0�̸� ���ڷ�
					tmp[i] = (char) (Math.random() * 10 + '0') ;
				}else { //1�̸� ���ĺ�
					tmp[i] = (char) (Math.random() * 26 + 'A') ;
				}
			}
			
		}else {
			logger.error("�������ڻ��� ������ ���� : " + size);
			throw new Exception("�������ڻ��� ������ ���� : ");
		}		
		return new String(tmp);
	}
	
	// ���� �ð� ��ȸ
	public String getCurrentTime() throws Exception {
		
		String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String current_time = simpleDateFormat.format(new Date());
		
		return current_time;
	}

}
