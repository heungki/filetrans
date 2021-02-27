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
public class FileTransController {
    
    @Autowired
    private ServerInfoRepository serverInfoRepository;    
    @Autowired
    private TransInfoRepository transInfoRepository;    
    @Autowired
    private TransLogRepository translogRepository;
           
	// 서버 리스트 조회
	@RequestMapping("/serverinfo") 
	public List<Server_Info> getServerInfo() {
		return serverInfoRepository.findAll(); 	 
	}
	
	// 서버ID로 서버정보 조회	
	@RequestMapping("/serverinfo/{serverid}")
	public Server_Info getServerInfoByserverid(@PathVariable("serverid") String server_id) {
		return serverInfoRepository.findById(server_id).get(); 
	}
	
    // 송신 정보 조회
    @RequestMapping("/transinfo")
    public List<Trans_Info> getTransInfo() {
        return transInfoRepository.findAll();
    }
    
    // 송신 로그 조회   
    @RequestMapping("/translog")
    public List<Trans_Log> getTransLog() {
        return translogRepository.findAll();
    }
    

}