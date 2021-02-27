package com.filetransdaemon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
	// common
    private static String bootstrapServer = "localhost:9092"; //서버정보
    private static int sessiontimeout = 200000; // 세션타임아웃(200초)
    private static int connectiontimeout = 15000; // 커넥션타임아웃(15초)
    
    // comsumer
    private static String keydeserializer = "org.apache.kafka.common.serialization.StringDeserializer";
    private static String valuedeserializer = "org.springframework.kafka.support.serializer.JsonDeserializer";
    
    // produce
    private static String keyserializer = "org.apache.kafka.common.serialization.StringSerializer";
    private static String valueserializer = "org.springframework.kafka.support.serializer.JsonSerializer";
    private static String filetranstopic = "filetrans-topic";
    
    @Bean
    public static String filetranstopic() {
		return filetranstopic;
	}
	public static void setFiletranstopic(String filetranstopic) {
		KafkaTopicConfig.filetranstopic = filetranstopic;
	}
	@Bean
	public static String bootstrapServer() {
		return bootstrapServer;
	}
	
	@Bean
	public static int sessiontimeout() {
		return sessiontimeout;
	}
	public static void setSessiontimeout(int sessiontimeout) {
		KafkaTopicConfig.sessiontimeout = sessiontimeout;
	}
	@Bean
	public static int connectiontimeout() {
		return connectiontimeout;
	}
	public static void setConnectiontimeout(int connectiontimeout) {
		KafkaTopicConfig.connectiontimeout = connectiontimeout;
	}
	@Bean
	public static String keyserializer() {
		return keyserializer;
	}
	public static void setKeyserializer(String keyserializer) {
		KafkaTopicConfig.keyserializer = keyserializer;
	}
	@Bean
	public static String valueserializer() {
		return valueserializer;
	}
	public static void setValueserializer(String valueserializer) {
		KafkaTopicConfig.valueserializer = valueserializer;
	}
	@Bean
	public static String keydeserializer() {
		return keydeserializer;
	}
	public static void setKeydeserializer(String keydeserializer) {
		KafkaTopicConfig.keydeserializer = keydeserializer;
	}
	@Bean
	public static String valuedeserializer() {
		return valuedeserializer;
	}
	public static void setValuedeserializer(String valuedeserializer) {
		KafkaTopicConfig.valuedeserializer = valuedeserializer;
	}
}