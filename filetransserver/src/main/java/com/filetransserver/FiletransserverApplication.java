package com.filetransserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FiletransserverApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(FiletransserverApplication.class);
		//application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

}
