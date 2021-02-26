package com.filetransclient.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.filetransclient.service.KafkaTopicService;
import com.filetransclient.util.FileTransUtil;
import com.google.gson.JsonObject;

@Controller
public class FileTransController {	
	private Logger logger = LoggerFactory.getLogger(FileTransController.class);
	

	// topic 생성 method
    public Map<String, String> createTopic(Map<String, String> filetransinfo) {
		KafkaTopicService cttopic= new KafkaTopicService();
		FileTransUtil ftutil = new FileTransUtil();
		Map<String, String> ctmap = new HashMap<String, String>(filetransinfo);
		String topicname = "";
		
    	try {
    		// kafka 서버 정보 확인
    		cttopic.getBootstrapServer();    		
    		
    		// kafka 배치 송신용 topic 생성(프로그램명+랜덤문자숫자5자리)	
    		topicname = filetransinfo.get("topicname-prefix").toString()+"-"+ftutil.getRandomStr(5);    		
    		filetransinfo.put("topicname", topicname);
    		    		
    		// kafka topic 생성
    		cttopic.createTopic(filetransinfo);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	filetransinfo.put("topicname", topicname);
    	
        return ctmap;
    }
    
    // 파일 전송 method
    public Map<String, String> fileTransReq(Map<String, String> filetransinfo) {
		KafkaTopicService ftreq= new KafkaTopicService();
		//FileTransUtil ftutil = new FileTransUtil();
		Map<String, String> ftrmap = new HashMap<String, String>(filetransinfo);
		
    	try {
    		// kafka 서버 정보 확인
    		ftreq.getBootstrapServer();    		
    		    		    		
    		// kafka producer 메시지 전송
    		ftreq.fileTransReq(ftrmap);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return ftrmap;
    }
    
    
}
