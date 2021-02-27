package com.filetransserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.filetransserver.model.Trans_Log;

@Repository
public interface TransLogRepository extends JpaRepository<Trans_Log, String> {

}