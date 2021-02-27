package com.filetransserver.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.filetransserver.model.Server_Info;
import com.filetransserver.model.Trans_Info;
import com.filetransserver.repository.ServerInfoRepository;
import com.filetransserver.repository.TransInfoRepository;

public class ServerInfoService {
	private static Logger logger = LoggerFactory.getLogger(ServerInfoService.class);
	
	private final ServerInfoRepository serverInfoRepository;

    @Autowired
    public ServerInfoService(ServerInfoRepository serverInfoRepository) {
        this.serverInfoRepository = serverInfoRepository;
    }
    
    // 서버 정보 가져오기
	public Server_Info getServerInfo(Server_Info server_Info) {		
		
		String server_id = server_Info.getServer_id();
		logger.info("server_id -> " + server_id);
		Optional<Server_Info> entity = serverInfoRepository.findById(server_id);
		Server_Info result= entity.get();
		logger.info("Server_nm -> " + result.getServer_nm());
		return result;
	}

}
