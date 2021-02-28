package com.filetransclient.model;

public class TransFileModel {
	private String trans_id; //���� ID
	private String trans_key; // ���� Key
	private String client_id; // Ŭ���̾�Ʈ ID
	private String reply_yn; // �����⿩��
	private String src_file; // �ҽ�����
	private String tgt_file; // Ÿ������
	private String temp_topic; // �ӽ� Topic
	private String client_time; // Ŭ���̾�Ʈ ó���ð�
	private String proc_code; // ó���ڵ�
	
	// ��������
	private String trans_type; // ����Ÿ��
	private String src_server_id; // �ҽ�����ID
	private String src_dir; // �ҽ����
	private String tgt_server_id; // Ÿ�ϼ���ID
	private String tgt_dir; // Ÿ�ϰ��
	private String enc_yn; // ��ȣȭ����
	private String enc_type; // ��ȣȭŸ��
	private String src_flag_yn; // �ҽ��÷���
	private String tgt_flag_yn; // Ÿ���÷���
	private String tgt_app; // Ÿ��APP
	private String file_size; // ���ϻ�����	
	
	// ��������
	private String server_ip; // Ÿ�ϼ���IP
	private String server_port; // Ÿ�ϼ���PORT
	private String ftp_id; // Ÿ�ϼ��� FTP ID
	private String password; // Ÿ�ϼ��� FTP �н�����
		
	// ����
	private String daemon_dc; // ���� ����
	private String encfile_size; // ��ȣȭ ���ϻ�����
		
	
	public String getTrans_id() {
		return trans_id;
	}
	public void setTrans_id(String trans_id) {
		this.trans_id = trans_id;
	}
	public String getTrans_key() {
		return trans_key;
	}
	public void setTrans_key(String trans_key) {
		this.trans_key = trans_key;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getReply_yn() {
		return reply_yn;
	}
	public void setReply_yn(String reply_yn) {
		this.reply_yn = reply_yn;
	}
	public String getSrc_file() {
		return src_file;
	}
	public void setSrc_file(String src_file) {
		this.src_file = src_file;
	}
	public String getTgt_file() {
		return tgt_file;
	}
	public void setTgt_file(String tgt_file) {
		this.tgt_file = tgt_file;
	}
	public String getTemp_topic() {
		return temp_topic;
	}
	public void setTemp_topic(String temp_topic) {
		this.temp_topic = temp_topic;
	}
	public String getClient_time() {
		return client_time;
	}
	public void setClient_time(String client_time) {
		this.client_time = client_time;
	}
	public String getProc_code() {
		return proc_code;
	}
	public void setProc_code(String proc_code) {
		this.proc_code = proc_code;
	}
	public String getTrans_type() {
		return trans_type;
	}
	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}
	public String getSrc_dir() {
		return src_dir;
	}
	public void setSrc_dir(String src_dir) {
		this.src_dir = src_dir;
	}
	public String getTgt_server_id() {
		return tgt_server_id;
	}
	public void setTgt_server_id(String tgt_server_id) {
		this.tgt_server_id = tgt_server_id;
	}
	public String getTgt_dir() {
		return tgt_dir;
	}
	public void setTgt_dir(String tgt_dir) {
		this.tgt_dir = tgt_dir;
	}
	public String getEnc_yn() {
		return enc_yn;
	}
	public void setEnc_yn(String enc_yn) {
		this.enc_yn = enc_yn;
	}
	public String getEnc_type() {
		return enc_type;
	}
	public void setEnc_type(String enc_type) {
		this.enc_type = enc_type;
	}
	public String getSrc_flag_yn() {
		return src_flag_yn;
	}
	public void setSrc_flag_yn(String src_flag_yn) {
		this.src_flag_yn = src_flag_yn;
	}
	public String getTgt_flga_yn() {
		return tgt_flag_yn;
	}
	public void setTgt_flag_yn(String tgt_flag_yn) {
		this.tgt_flag_yn = tgt_flag_yn;
	}
	public String getTgt_app() {
		return tgt_app;
	}
	public void setTgt_app(String tgt_app) {
		this.tgt_app = tgt_app;
	}
	public String getTgt_server_ip() {
		return server_ip;
	}
	public void setTgt_server_ip(String server_ip) {
		this.server_ip = server_ip;
	}
	public String getServer_ip() {
		return server_ip;
	}
	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}
	public String getServer_port() {
		return server_port;
	}
	public void setServer_port(String server_port) {
		this.server_port = server_port;
	}
	public String getFtp_id() {
		return ftp_id;
	}
	public void setFtp_id(String ftp_id) {
		this.ftp_id = ftp_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getSrc_server_id() {
		return src_server_id;
	}
	public void setSrc_server_id(String src_server_id) {
		this.src_server_id = src_server_id;
	}
	
	public String getDaemon_dc() {
		return daemon_dc;
	}
	public void setDaemon_dc(String daemon_dc) {
		this.daemon_dc = daemon_dc;
	}
	public String getEncfile_size() {
		return encfile_size;
	}
	public void setEncfile_size(String encfile_size) {
		this.encfile_size = encfile_size;
	}
	@Override
	public String toString() {
		return "TransFileModel [trans_id=" + trans_id + ", trans_key=" + trans_key + ", client_id=" + client_id
				+ ", reply_yn=" + reply_yn + ", src_file=" + src_file + ", tgt_file=" + tgt_file + ", temp_topic="
				+ temp_topic + ", client_time=" + client_time + ", proc_code=" + proc_code + ", trans_type="
				+ trans_type + ", src_server_id=" + src_server_id + ", src_dir=" + src_dir + ", tgt_server_id="
				+ tgt_server_id + ", tgt_dir=" + tgt_dir + ", enc_yn=" + enc_yn + ", enc_type=" + enc_type
				+ ", src_flag_yn=" + src_flag_yn + ", tgt_flga_yn=" + tgt_flag_yn + ", tgt_app=" + tgt_app
				+ ", file_size=" + file_size + ", server_ip=" + server_ip + ", server_port=" + server_port + ", ftp_id="
				+ ftp_id + ", password=" + password + ", daemon_dc=" + daemon_dc + ", encfile_size=" + encfile_size
				+ "]";
	}
	
	
	
}
