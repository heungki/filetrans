package com.filetransserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filetransserver.model.Trans_Info;

@Repository
public interface TransInfoRepository extends JpaRepository<Trans_Info, String> {

}
