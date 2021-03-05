package com.filetransclient;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.kafka.core.KafkaTemplate;

import com.filetransclient.controller.FileTransController;
import com.filetransclient.model.TransFileModel;
import com.filetransclient.util.FileCheck;

@SpringBootApplication
public class FiletransclientApplication {
	private static Logger logger = LoggerFactory.getLogger(FiletransclientApplication.class);
	
	
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(FiletransclientApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		ConfigurableApplicationContext context = application.run(args);
		
		TransFileModel fileTransModel =  new TransFileModel();
		FileTransController fileTransController = context.getBean(FileTransController.class);
				
		logger.info("파일 전송 배치 시작");
		// 클라이언트 ID(각 배치에서)
		String client_id = args[0];		
		fileTransModel.setClient_id(client_id);
		
		// 전송 ID(각 배치에서)
		String tran_id = args[1];		
		
		// 응답대기여부(각 배치에서)
		String reply_yn = "Y";
		
		// 소스파일명(각 배치에서)
		String src_dir = "BIZ-send/";
		String src_file = args[2];
		
		
		
		// 파일생성(테스트를 위한 임시 소스파일 생성)
		if(!fileTransController.tempFileCreate(src_dir+src_file)) {
			logger.error("파일 생성 실패");
			System.exit(0);
		}
		
		// 타켓파일(각 배치에서)
		String tgt_file = args[3];
		
		// 파일정보 세팅		
		fileTransModel.setTrans_id(tran_id);
		fileTransModel.setReply_yn(reply_yn);
		fileTransModel.setSrc_file(src_file);
		fileTransModel.setTgt_file(tgt_file);
		// 파일사이즈
		// 파일 사이즈 확인		
		FileCheck fileCheck = new FileCheck();
		fileTransModel.setFile_size(fileCheck.getFileSize(src_dir+"/"+src_file));		
		
		// 파일 전송 전문 세팅
		fileTransModel = fileTransController.fileTransSet(fileTransModel);
				
		// 파일 전송 결과 수신용 topic 생성 및 세팅
		fileTransModel.setTemp_topic(fileTransController.createTopic(fileTransModel));
		
		// 파일 전송 요청 producer
		if(!fileTransController.fileTransReq(fileTransModel)) {
			logger.error("File Transfer Request Success");
			System.exit(0);
		}
		
		TransFileModel tranResult =  new TransFileModel();
		// 파일 전송 결과 수신(임시 Topic)
		logger.info("File Transfer Response Waiting...");
		tranResult = fileTransController.fileTransRes(fileTransModel.getTemp_topic());
		
		logger.info(tranResult.toString());
		
		// 파일 전송 결과 코드(정상 SS001)
		if(tranResult.getProc_code().equals("SS001")) {
			
			logger.info("File Transfer Success!!");
		}else {
			
			logger.info("File Transfer Fail!!" + tranResult.getProc_code());
		}
		
		logger.info("File Transfer End");
	}

}
