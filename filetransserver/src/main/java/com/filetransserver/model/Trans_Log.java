package com.filetransserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor    
@NoArgsConstructor       
@Entity
public class Trans_Log {

	private String trans_date; // 전송날짜
	private String trans_id; // 전송ID
	private String trans_key; // 전송키
	private String client_id; // 클라이언트 ID
	private String reply_yn; // 응답대기 여부
	private String src_dir; // 소스경로
	private String src_file; // 소스파일
	private String tgt_dir; // 타켓경로
	private String tgt_file; // 타켓파일				
	private String file_size; // 파일사이즈
	private String encfile_size; // 암호화파일사이즈	
	private String temp_topic; // 임시토픽
	private String client_time; // 클라이언트처리시간
	private String proc_code; // 처리코드
	private String server_time; // 서버처리시간
	private String status; // 상태
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String log_seq; // 로그시퀀스
	public String getTrans_date() {
		return trans_date;
	}
	public String getTrans_id() {
		return trans_id;
	}
	public String getTrans_key() {
		return trans_key;
	}
	public String getClient_id() {
		return client_id;
	}
	public String getReply_yn() {
		return reply_yn;
	}
	public String getSrc_dir() {
		return src_dir;
	}
	public String getSrc_file() {
		return src_file;
	}
	public String getTgt_dir() {
		return tgt_dir;
	}
	public String getTgt_file() {
		return tgt_file;
	}
	public String getFile_size() {
		return file_size;
	}
	public String getTemp_topic() {
		return temp_topic;
	}
	public String getClient_time() {
		return client_time;
	}
	public String getProc_code() {
		return proc_code;
	}
	public String getServer_time() {
		return server_time;
	}
	public String getStatus() {
		return status;
	}
	public String getLog_seq() {
		return log_seq;
	}
	public void setTrans_date(String trans_date) {
		this.trans_date = trans_date;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	public void setTrans_key(String trans_key) {
		this.trans_key = trans_key;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public void setReply_yn(String reply_yn) {
		this.reply_yn = reply_yn;
	}
	public void setSrc_dir(String src_dir) {
		this.src_dir = src_dir;
	}
	public void setSrc_file(String src_file) {
		this.src_file = src_file;
	}
	public void setTgt_dir(String tgt_dir) {
		this.tgt_dir = tgt_dir;
	}
	public void setTgt_file(String tgt_file) {
		this.tgt_file = tgt_file;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public void setTemp_topic(String temp_topic) {
		this.temp_topic = temp_topic;
	}
	public void setClient_time(String client_time) {
		this.client_time = client_time;
	}
	public void setProc_code(String proc_code) {
		this.proc_code = proc_code;
	}
	public void setServer_time(String server_time) {
		this.server_time = server_time;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setLog_seq(String log_seq) {
		this.log_seq = log_seq;
	}
	public String getEncfile_size() {
		return encfile_size;
	}
	public void setEncfile_size(String encfile_size) {
		this.encfile_size = encfile_size;
	}
	
}
