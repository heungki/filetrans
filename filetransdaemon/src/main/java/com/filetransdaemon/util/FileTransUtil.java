package com.filetransdaemon.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.filetransdaemon.model.TransFileModel;

public class FileTransUtil {
	private Logger logger = LoggerFactory.getLogger(FileTransUtil.class);

	// ���� �ð� ��ȸ
	public String getCurrentTime() throws Exception {
		
		String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String current_time = simpleDateFormat.format(new Date());
		
		return current_time;
	}
	
	// ���ϸ� ����
	public boolean renameFile(TransFileModel transFileModel, String surffix) {
		boolean result = true;
		
		String proc_srcFilepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file() + surffix);
		String proc_dstFilepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file());
		File file = new File(proc_srcFilepath);
		File fileNew = new File(proc_dstFilepath);
		file.renameTo( fileNew );
		

		return result;
	}
	
	// ���� ���� Ȯ��
	public boolean chkFileExists(String filepath) {
		boolean result = true;
				
		File tmp = new File(filepath);
		
		if(!tmp.exists()) {
			return false;
		}
		
		return result;
	}
	
	// ���� ������ Ȯ��
	public String getFileSize(String filepath) {
		long filesize = 0;
		
		File tmp = new File(filepath);
		
		if(tmp.exists()) {
			filesize = tmp.length();
		}
		
		logger.info("File size: " + filesize);
		
		return Long.toString(filesize);
	}
	
	// Flag ���� ����
	public void createFlagFile(TransFileModel transFileModel, String surffix) throws IOException {
		String srcfilepath = FilenameUtils.concat(transFileModel.getSrc_dir(), transFileModel.getSrc_file());
		String dstfilepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file()); 
		String filesize = transFileModel.getFile_size();
		
		File file;
        // ���� ��ü ����
		if(transFileModel.getDaemon_dc().equals("S")) {
			file = new File(srcfilepath+surffix);
		}else if(transFileModel.getDaemon_dc().equals("R")) {
			file = new File(dstfilepath+surffix);
		}else {
			return;
		}
        
        FileWriter fw = new FileWriter(file, true) ;
         
        fw.write("srcfilepath: " + srcfilepath + "\r\n");
        fw.write("dstfilepath: " + dstfilepath + "\r\n");
        fw.write("filesize: " + filesize);
        
        fw.flush();
        fw.close();
 	}

}
