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
	

	// topic ���� method
    public Map<String, String> createTopic(Map<String, String> filetransinfo) {
		KafkaTopicService cttopic= new KafkaTopicService();
		FileTransUtil ftutil = new FileTransUtil();
		Map<String, String> ctmap = new HashMap<String, String>(filetransinfo);
		String topicname = "";
		
    	try {
    		// kafka ���� ���� Ȯ��
    		cttopic.getBootstrapServer();    		
    		
    		// kafka ��ġ �۽ſ� topic ����(���α׷���+�������ڼ���5�ڸ�)	
    		topicname = filetransinfo.get("topicname-prefix").toString()+"-"+ftutil.getRandomStr(5);    		
    		filetransinfo.put("topicname", topicname);
    		    		
    		// kafka topic ����
    		cttopic.createTopic(filetransinfo);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	filetransinfo.put("topicname", topicname);
    	
        return ctmap;
    }
    
    // ���� ���� method
    public Map<String, String> fileTransReq(Map<String, String> filetransinfo) {
		KafkaTopicService ftreq= new KafkaTopicService();
		//FileTransUtil ftutil = new FileTransUtil();
		Map<String, String> ftrmap = new HashMap<String, String>(filetransinfo);
		
    	try {
    		// kafka ���� ���� Ȯ��
    		ftreq.getBootstrapServer();    		
    		    		    		
    		// kafka producer �޽��� ����
    		ftreq.fileTransReq(ftrmap);
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return ftrmap;
    }
    
    
}
