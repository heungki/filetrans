insert into person values(1,'HONG', 'seoul 523-23'); insert into person values(2,'CHOI', 'seoul 212-3');


-- 전송 정보 입력
-- 정상전송
insert into trans_info(trans_id, trans_nm, trans_type, src_server_id, src_dir, tgt_server_id, tgt_dir, enc_yn, enc_type, src_flag_yn, tgt_flag_yn, tgt_app)
	values('CUSTOMER_INFO', '고객정보', 'S', 'BIZ', 'BIZ-send/', 'CHN', 'CHN-recv/', 'Y', 'AES', 'Y', 'Y', 'notepad');

-- flag 미존재
insert into trans_info(trans_id, trans_nm, trans_type, src_server_id, src_dir, tgt_server_id, tgt_dir, enc_yn, enc_type, src_flag_yn, tgt_flag_yn, tgt_app)
	values('ACCOUNT_INFO', '계좌정보', 'S', 'BIZ', 'BIZ-send/', 'CHN', 'CHN-recv/', 'Y', 'AES', 'N', 'N', '');

-- 암호화X
insert into trans_info(trans_id, trans_nm, trans_type, src_server_id, src_dir, tgt_server_id, tgt_dir, enc_yn, enc_type, src_flag_yn, tgt_flag_yn, tgt_app)
	values('ISSUED_INFO', '발급정보', 'S', 'BIZ', 'BIZ-send/', 'CHN', 'CHN-recv/', 'N', '', 'Y', 'N', 'bat_exec.sh ISSUED_INF_RECV');

-- 서버 정보 입력
insert into server_info(server_id, server_nm, server_ip, server_port, trans_type, ftp_id, password)
	values('BIZ', '계정계', '127.0.0.1', '22', 'S','bizftp', 'bizftp!');

insert into server_info(server_id, server_nm, server_ip, server_port, trans_type, ftp_id, password)
	values('CHN', '채널계', '127.0.0.1', '22', 'S','bizftp', 'bizftp!');

insert into server_info(server_id, server_nm, server_ip, server_port, trans_type, ftp_id, password)
	values('FEP', '대외계', '172.0.10.2', '21', 'F','fepftp', 'fepftp!');


-- 로그 입력(테스트 조회용)
--insert into trans_log( trans_date, trans_id, trans_key, client_id, reply_yn, src_dir, src_file, tgt_dir, tgt_file, file_size, temp_topic, client_time, proc_code, server_time, status)
--	values(TO_CHAR(sysdate,'YYYYMMDD'), 'ISSUED_INFO', 'testbatch01-12345', 'testbatch01', 'N', '', 'testfile.txt', '', 'testfile_dst.txt', '', 'tmp-testbatch01-12345', TO_CHAR(sysdate,'yyyyMMddHH24miSSsss'), '', TO_CHAR(sysdate,'yyyyMMddHH24miSSsss'), 'START');

--insert into trans_log( trans_date, trans_id, trans_key, client_id, reply_yn, src_dir, src_file, tgt_dir, tgt_file, file_size, temp_topic, client_time, proc_code, server_time, status)
--	values(TO_CHAR(sysdate,'YYYYMMDD'), 'ISSUED_INFO', 'testbatch01-12345', 'testbatch01', 'N', '', 'testfile.txt', '', 'testfile_dst.txt', '', 'tmp-testbatch01-12345', TO_CHAR(sysdate,'yyyyMMddHH24miSSsss'), '', TO_CHAR(LOCALTIMESTAMP + INTERVAL '1' SECOND,'yyyyMMddHH24miSSsss'), 'SEND');

--insert into trans_log( trans_date, trans_id, trans_key, client_id, reply_yn, src_dir, src_file, tgt_dir, tgt_file, file_size, temp_topic, client_time, proc_code, server_time, status)
--	values(TO_CHAR(sysdate,'YYYYMMDD'), 'ISSUED_INFO', 'testbatch01-12345', 'testbatch01', 'N', '/snd', 'testfile.txt', '/rev', 'testfile_dst.txt', '12345', 'tmp-testbatch01-12345', TO_CHAR(sysdate,'yyyyMMddHH24miSSsss'), '', TO_CHAR(LOCALTIMESTAMP + INTERVAL '2' SECOND,'yyyyMMddHH24miSSsss'), 'RECV');

--insert into trans_log( trans_date, trans_id, trans_key, client_id, reply_yn, src_dir, src_file, tgt_dir, tgt_file, file_size, temp_topic, client_time, proc_code, server_time, status)
--	values(TO_CHAR(sysdate,'YYYYMMDD'), 'ISSUED_INFO', 'testbatch01-12345', 'testbatch01', 'N', '/snd', 'testfile.txt', '/rev', 'testfile_dst.txt', '12345', 'tmp-testbatch01-12345', TO_CHAR(sysdate,'yyyyMMddHH24miSSsss'), '', TO_CHAR(LOCALTIMESTAMP + INTERVAL '3' SECOND,'yyyyMMddHH24miSSsss'), 'TARGET');

--insert into trans_log( trans_date, trans_id, trans_key, client_id, reply_yn, src_dir, src_file, tgt_dir, tgt_file, file_size, temp_topic, client_time, proc_code, server_time, status)
--	values(TO_CHAR(sysdate,'YYYYMMDD'), 'ISSUED_INFO', 'testbatch01-12345', 'testbatch01', 'N', '/snd', 'testfile.txt', '/rev', 'testfile_dst.txt', '12345', 'tmp-testbatch01-12345', TO_CHAR(sysdate,'yyyyMMddHH24miSSsss'), 'SS001', TO_CHAR(LOCALTIMESTAMP + INTERVAL '4' SECOND,'yyyyMMddHH24miSSsss'), 'RESULT');

--insert into trans_log( trans_date, trans_id, trans_key, client_id, reply_yn, src_dir, src_file, tgt_dir, tgt_file, file_size, temp_topic, client_time, proc_code, server_time, status)
--	values(TO_CHAR(sysdate,'YYYYMMDD'), 'ISSUED_INFO', 'testbatch01-12345', 'testbatch01', 'N', '/snd', 'testfile.txt', '/rev', 'testfile_dst.txt', '12345', 'tmp-testbatch01-12345', TO_CHAR(sysdate,'yyyyMMddHH24miSSsss'), 'SS001', TO_CHAR(LOCALTIMESTAMP + INTERVAL '5' SECOND,'yyyyMMddHH24miSSsss'), 'END');
	