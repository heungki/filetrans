package com.filetransserver.controller;

import com.filetransserver.model.Server_Info;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.model.Trans_Log;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.filetransserver.repository.TransLogRepository;
import com.filetransserver.service.KafkaProducerService;
import com.filetransserver.service.ServerInfoService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileTransRestController {
    
    @Autowired
    private ServerInfoRepository serverInfoRepository;    
    @Autowired
    private TransInfoRepository transInfoRepository;    
    @Autowired
    private TransLogRepository translogRepository;
           
	// ���� ����Ʈ ��ȸ
	@RequestMapping("/serverinfo") 
	public List<Server_Info> getServerInfo() {
		return serverInfoRepository.findAll(); 	 
	}
	
	// ����ID�� �������� ��ȸ	
	@RequestMapping("/serverinfo/{serverid}")
	public Server_Info getServerInfoByserverid(@PathVariable("serverid") String server_id) {
		return serverInfoRepository.findById(server_id).get(); 
	}
	
    // �۽� ���� ��ȸ
    @RequestMapping("/transinfo")
    public List<Trans_Info> getTransInfo() {
    	List<Trans_Info> transinfo = new ArrayList<Trans_Info>();
    	transInfoRepository.findAll().forEach(transinfo::add);
        return  transinfo;     
    }
    
    // �۽� �α� ��ȸ   
    @RequestMapping("/translog")
    public List<Trans_Log> getTransLog() {
        return translogRepository.findAll();
    }     

}