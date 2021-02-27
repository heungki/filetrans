package com.filetransserver.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//
@Data 
@AllArgsConstructor    
@NoArgsConstructor       
@Entity
public class Server_Info {
	@Id @GeneratedValue
	private String server_id; // 서버 ID
	private String server_nm; // 서버 명
	private String server_ip; // 서버 IP
	private String server_port; // 서버 PORT
	private String trans_type; // 전송 타입
	private String ftp_id; // FTP ID
	private String password; // FTP 패스워드	
	public String getServer_id() {
		return server_id;
	}
	public String getServer_nm() {
		return server_nm;
	}
	public String getServer_ip() {
		return server_ip;
	}
	public String getServer_port() {
		return server_port;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public String getFtp_id() {
		return ftp_id;
	}
	public String getPassword() {
		return password;
	}
	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}
	public void setServer_nm(String server_nm) {
		this.server_nm = server_nm;
	}
	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}
	public void setServer_port(String server_port) {
		this.server_port = server_port;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public void setFtp_id(String ftp_id) {
		this.ftp_id = ftp_id;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
