package com.product_service.product_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig{

    @Bean(name = "productKafkaTopicConfig")
    public KafkaTopicConfig kafkaTopicConfig() {
        return new KafkaTopicConfig();
    }
    @Bean
    public NewTopic createProductTopic() {
        return new NewTopic("product.created", 1, (short) 1);  // name, partitions, replicas
    }
    @Bean
    public NewTopic createOrderTopic() {
        return new NewTopic("order.created", 1, (short) 1);  // name, partitions, replicas
    }
}
