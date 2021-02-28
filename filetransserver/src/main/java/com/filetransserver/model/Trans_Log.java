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

	private String trans_date; // ���۳�¥
	private String trans_id; // ����ID
	private String trans_key; // ����Ű
	private String client_id; // Ŭ���̾�Ʈ ID
	private String reply_yn; // ������ ����
	private String src_dir; // �ҽ����
	private String src_file; // �ҽ�����
	private String tgt_dir; // Ÿ�ϰ��
	private String tgt_file; // Ÿ������				
	private String file_size; // ���ϻ�����
	private String encfile_size; // ��ȣȭ���ϻ�����	
	private String temp_topic; // �ӽ�����
	private String client_time; // Ŭ���̾�Ʈó���ð�
	private String proc_code; // ó���ڵ�
	private String server_time; // ����ó���ð�
	private String status; // ����
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private String log_seq; // �α׽�����
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
