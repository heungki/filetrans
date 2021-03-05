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
				
		logger.info("���� ���� ��ġ ����");
		// Ŭ���̾�Ʈ ID(�� ��ġ����)
		String client_id = args[0];		
		fileTransModel.setClient_id(client_id);
		
		// ���� ID(�� ��ġ����)
		String tran_id = args[1];		
		
		// �����⿩��(�� ��ġ����)
		String reply_yn = "Y";
		
		// �ҽ����ϸ�(�� ��ġ����)
		String src_dir = "BIZ-send/";
		String src_file = args[2];
		
		
		
		// ���ϻ���(�׽�Ʈ�� ���� �ӽ� �ҽ����� ����)
		if(!fileTransController.tempFileCreate(src_dir+src_file)) {
			logger.error("���� ���� ����");
			System.exit(0);
		}
		
		// Ÿ������(�� ��ġ����)
		String tgt_file = args[3];
		
		// �������� ����		
		fileTransModel.setTrans_id(tran_id);
		fileTransModel.setReply_yn(reply_yn);
		fileTransModel.setSrc_file(src_file);
		fileTransModel.setTgt_file(tgt_file);
		// ���ϻ�����
		// ���� ������ Ȯ��		
		FileCheck fileCheck = new FileCheck();
		fileTransModel.setFile_size(fileCheck.getFileSize(src_dir+"/"+src_file));		
		
		// ���� ���� ���� ����
		fileTransModel = fileTransController.fileTransSet(fileTransModel);
				
		// ���� ���� ��� ���ſ� topic ���� �� ����
		fileTransModel.setTemp_topic(fileTransController.createTopic(fileTransModel));
		
		// ���� ���� ��û producer
		if(!fileTransController.fileTransReq(fileTransModel)) {
			logger.error("File Transfer Request Success");
			System.exit(0);
		}
		
		TransFileModel tranResult =  new TransFileModel();
		// ���� ���� ��� ����(�ӽ� Topic)
		logger.info("File Transfer Response Waiting...");
		tranResult = fileTransController.fileTransRes(fileTransModel.getTemp_topic());
		
		logger.info(tranResult.toString());
		
		// ���� ���� ��� �ڵ�(���� SS001)
		if(tranResult.getProc_code().equals("SS001")) {
			
			logger.info("File Transfer Success!!");
		}else {
			
			logger.info("File Transfer Fail!!" + tranResult.getProc_code());
		}
		
		logger.info("File Transfer End");
	}

}
