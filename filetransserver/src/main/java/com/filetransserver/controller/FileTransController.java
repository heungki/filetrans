package com.filetransserver.controller;

import com.filetransserver.model.Server_Info;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.model.Trans_Log;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;
import com.filetransserver.repository.TransLogRepository;
import com.filetransserver.service.KafkaProducerService;
import com.filetransserver.service.ServerInfoService;
import com.filetransserver.service.TransInfoService;
import com.filetransserver.service.TransLogService;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class FileTransController {
	private static Logger logger = LoggerFactory.getLogger(FileTransController.class);
	
    @Autowired
    private ServerInfoRepository serverInfoRepository;    
    @Autowired
    private TransInfoRepository transInfoRepository;    
    @Autowired
    private TransLogRepository transLogRepository;
  
    // 서버리스트 조회
    @RequestMapping("/serverlist")
    public String serverList(Model model) {
    	ServerInfoService serverInfoService = new ServerInfoService(serverInfoRepository);
        List<Server_Info> serverList = serverInfoService.getServerList();
        //logger.info("Server_nm -> " + serverList.get(0).getServer_nm());
        
        model.addAttribute("serverlist", serverList);
        return "serverlist.html";
    }
    
    // 송신정보리스트 조회
    @RequestMapping("/transinfolist")
    public String transInfoList(Model model) {
    	TransInfoService transInfoService = new TransInfoService(transInfoRepository);
        List<Trans_Info> transInfoList = transInfoService.getTransInfoList();
        //logger.info("Server_nm -> " + serverList.get(0).getServer_nm());
        
        model.addAttribute("transinfolist", transInfoList);
        return "transinfolist.html";
    }
    
    // 송신로그리스트 조회
    @RequestMapping("/transloglist")
    public String transLogList(Model model) {
    	TransLogService transLogService = new TransLogService(transLogRepository);
        List<Trans_Log> transLogList = transLogService.getTransLogList();
        //logger.info("Server_nm -> " + serverList.get(0).getServer_nm());
        
        model.addAttribute("transloglist", transLogList);
        return "transloglist.html";
    }
    

}