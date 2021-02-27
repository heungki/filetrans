package com.filetransserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filetransserver.model.Server_Info;

@Repository
public interface ServerInfoRepository extends JpaRepository<Server_Info, String> {
	
	//server_info findByserver_id(String server_id);

}

