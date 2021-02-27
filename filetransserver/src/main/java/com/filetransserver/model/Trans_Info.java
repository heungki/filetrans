package com.filetransserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor    
@NoArgsConstructor       
@Entity
public class Trans_Info {
	@Id @GeneratedValue
	private String trans_id; // 전송 ID
	private String trans_nm; // 전송 명
	private String trans_type; // 전송타입
	private String src_server_id; // 소스서버ID
	private String src_dir; // 소스경로
	private String tgt_server_id; // 타겟서버ID
	private String tgt_dir; // 타겟경로
	private String enc_yn; // 암호화여부
	private String enc_type; // 암호화타입				
	private String src_flag_yn; // 소스플래그
	private String tgt_flag_yn; // 타겟플래그
	private String tgt_app; // 타켓APP
	public String getTrans_id() {
		return trans_id;
	}
	public String getTrans_nm() {
		return trans_nm;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public String getSrc_server_id() {
		return src_server_id;
	}
	public String getSrc_dir() {
		return src_dir;
	}
	public String getTgt_server_id() {
		return tgt_server_id;
	}
	public String getTgt_dir() {
		return tgt_dir;
	}
	public String getEnc_yn() {
		return enc_yn;
	}
	public String getEnc_type() {
		return enc_type;
	}
	public String getSrc_flag_yn() {
		return src_flag_yn;
	}
	public String getTgt_flag_yn() {
		return tgt_flag_yn;
	}
	public String getTgt_app() {
		return tgt_app;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	public void setTrans_nm(String trans_nm) {
		this.trans_nm = trans_nm;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public void setSrc_server_id(String src_server_id) {
		this.src_server_id = src_server_id;
	}
	public void setSrc_dir(String src_dir) {
		this.src_dir = src_dir;
	}
	public void setTgt_server_id(String tgt_server_id) {
		this.tgt_server_id = tgt_server_id;
	}
	public void setTgt_dir(String tgt_dir) {
		this.tgt_dir = tgt_dir;
	}
	public void setEnc_yn(String enc_yn) {
		this.enc_yn = enc_yn;
	}
	public void setEnc_type(String enc_type) {
		this.enc_type = enc_type;
	}
	public void setSrc_flag_yn(String src_flag_yn) {
		this.src_flag_yn = src_flag_yn;
	}
	public void setTgt_flag_yn(String tgt_flag_yn) {
		this.tgt_flag_yn = tgt_flag_yn;
	}
	public void setTgt_app(String tgt_app) {
		this.tgt_app = tgt_app;
	}
	
	
}
