package com.filetransdaemon.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.filetransdaemon.model.TransFileModel;

public class FileTransUtil {
	private Logger logger = LoggerFactory.getLogger(FileTransUtil.class);

	// 현재 시간 조회
	public String getCurrentTime() throws Exception {
		
		String pattern = "yyyyMMddHHmmssSSS";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String current_time = simpleDateFormat.format(new Date());
		
		return current_time;
	}
	
	// 파일명 변경
	public boolean renameFile(TransFileModel transFileModel, String surffix) {
		boolean result = true;
		
		String proc_srcFilepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file() + surffix);
		String proc_dstFilepath = FilenameUtils.concat(transFileModel.getTgt_dir(), transFileModel.getTgt_file());
		File file = new File(proc_srcFilepath);
		File fileNew = new File(proc_dstFilepath);
		file.renameTo( fileNew );
		

		return result;
	}

}
