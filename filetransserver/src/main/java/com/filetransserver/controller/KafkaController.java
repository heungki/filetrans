package com.filetransserver.controller;

import com.filetransserver.model.Server_Info;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.model.Trans_Log;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.filetransserver.repository.TransLogRepository;
import com.filetransserver.service.KafkaProducerService;
import com.filetransserver.service.ServerInfoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {
    private final KafkaProducerService producer;

    @Autowired
    KafkaController(KafkaProducerService producer) {
        this.producer = producer;
    }
    
    @Autowired
    private ServerInfoRepository serverInfoRepository;    
    @Autowired
    private TransInfoRepository transInfoRepository;    
    @Autowired
    private TransLogRepository translogRepository;
    
    // filetrans-topic으로 message send 테스트
    @RequestMapping(method = RequestMethod.GET, path = "/topictest")
    String send() {
    	try {
			producer.sendMessage("filetrans-topic", "filetrans -> test message");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//producer.sendMessage("company-jjeaby-topic", "message key company", "company -> jjeaby message");
        return "Kafka Produce!!!";
    }

}