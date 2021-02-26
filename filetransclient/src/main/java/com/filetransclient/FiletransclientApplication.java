package com.filetransclient;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.filetransclient.controller.FileTransController;
import com.filetransclient.service.KafkaClientConfig;
import com.google.gson.Gson;

@SpringBootApplication
public class FiletransclientApplication {
	private static Logger logger = LoggerFactory.getLogger(FiletransclientApplication.class);
		
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(FiletransclientApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
		
		String clientname = "filetrans";
		
		FileTransController ftc = new FileTransController();
		// 파일정보 세팅
		Gson gson = new Gson();		
		Map<String, String> filetransinfo = new HashMap<String, String>();
		filetransinfo.put("topicname-prefix", clientname);		
		filetransinfo.put("message", "test message !!!!!!!!!!!");
		
		// 파일 전송 결과 수신용 topic 생성
		ftc.createTopic(filetransinfo);
		
		// 파일 전송 요청 producer
		ftc.fileTransReq(filetransinfo);
	}

}
