package com.filetransdaemon.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
	@Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
    @Value("${spring.kafka.producer.key-serializer}")
    private String keyDeSerializer;
    @Value("${spring.kafka.producer.value-serializer}")
    private String valueDeSerializer;
    @Value("${spring.kafka.connectiontimeout}")
    private String connectiontimeout;
    @Value("${spring.kafka.sessiontimeout}")
    private String sessiontimeout;
    
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keyDeSerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueDeSerializer);
        configProps.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, connectiontimeout);
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, sessiontimeout);
        return new DefaultKafkaProducerFactory<>(configProps);
    }


	@Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<String, String>(producerFactory());
    }    

}
