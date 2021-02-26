package com.filetransclient.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaClientConfig {
	//common
    private static String bootstrapServer = "localhost:9092"; //서버정보
    private static int sessiontimeout = 200000; // 세션타임아웃(200초)
    private static int connectiontimeout = 15000; // 커넥션타임아웃(15초)
    
    //produce
    private static String keyserializer = "org.apache.kafka.common.serialization.StringSerializer";
    private static String valueserializer = "org.apache.kafka.common.serialization.StringSerializer";
    private static String filetranstopic = "filetrans-topic";
    
    @Bean
    public static String filetranstopic() {
		return filetranstopic;
	}
	public static void setFiletranstopic(String filetranstopic) {
		KafkaClientConfig.filetranstopic = filetranstopic;
	}
	@Bean
	public static String bootstrapServer() {
		return bootstrapServer;
	}
	public static void setBootstrapServer(String bootstrapServer) {
		KafkaClientConfig.bootstrapServer = bootstrapServer;
	}
	@Bean
	public static int sessiontimeout() {
		return sessiontimeout;
	}
	public static void setSessiontimeout(int sessiontimeout) {
		KafkaClientConfig.sessiontimeout = sessiontimeout;
	}
	@Bean
	public static int connectiontimeout() {
		return connectiontimeout;
	}
	public static void setConnectiontimeout(int connectiontimeout) {
		KafkaClientConfig.connectiontimeout = connectiontimeout;
	}
	@Bean
	public static String keyserializer() {
		return keyserializer;
	}
	public static void setKeyserializer(String keyserializer) {
		KafkaClientConfig.keyserializer = keyserializer;
	}
	@Bean
	public static String valueserializer() {
		return valueserializer;
	}
	public static void setValueserializer(String valueserializer) {
		KafkaClientConfig.valueserializer = valueserializer;
	}
}