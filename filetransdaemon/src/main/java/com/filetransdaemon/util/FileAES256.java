package com.filetransdaemon.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
 
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.filetransdaemon.service.KafkaConsumerService;
 
@Service
public class FileAES256 {
	private static Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
	
	 FileInputStream inFile = null;
	 String iv = "1234567891234567";
	 byte[] key = "1234567891234567".getBytes();
	 FileOutputStream outFile = null;
	 Key getkey = new SecretKeySpec(key,"AES");
	 
	 public boolean encryptFile(String srcFilePath, String dstFilePath) throws Exception {
		 Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		 cipher.init(Cipher.ENCRYPT_MODE, getkey, new IvParameterSpec(iv.getBytes()));
		 
		 File file1 = new File(dstFilePath);
		 
		 inFile = new FileInputStream(srcFilePath);
		 outFile = new FileOutputStream(file1);
		 //file encryption
		 byte[] input = new byte[64];
		 int bytesRead;
	
		 while ((bytesRead = inFile.read(input)) != -1) {
		     byte[] output = cipher.update(input, 0, bytesRead);
		     if (output != null)
		         outFile.write(output);
		 }
	
		 byte[] output = cipher.doFinal();
		 if (output != null)
		     outFile.write(output);
	
		 inFile.close();
		 outFile.flush();
		 outFile.close();
		 
		 return true;
	 }
	 
	 public boolean decryptFile(String srcFilePath, String dstFilePath) throws Exception {
	     Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	     cipher.init(Cipher.DECRYPT_MODE, getkey,new IvParameterSpec(iv.getBytes()));
	     File file1 = new File(dstFilePath);
		 
		 inFile = new FileInputStream(srcFilePath);
		 outFile = new FileOutputStream(file1);
	     
		 
	     byte[] in = new byte[64];
	      int read;
	      while ((read = inFile.read(in)) != -1) {
	          byte[] output = cipher.update(in, 0, read);
	          if (output != null)
	        	  outFile.write(output);
	      }

	      byte[] output = cipher.doFinal();
	      if (output != null)
	    	  outFile.write(output);
	      inFile.close();
	      outFile.flush();
	      outFile.close();
		
	      return true;
	 }
	 
 
}