package com.filetransdaemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FiletransdaemonApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(FiletransdaemonApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
		
		aestest aestest= new aestest();
		sftptest sftptest= new sftptest();
		try {
			aestest.run(args);
			sftptest.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
