//package com.order_service.order_service.kafka;
//
//import org.apache.kafka.clients.admin.NewTopic;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OrderKafkaTopicConfig{
//
//    //renaming bean to avoid conflict as same bean KafkaTopicConfig present in productService
////    @Bean(name = "orderKafkaTopicConfig")
////    public KafkaTopicConfig kafkaTopicConfig() {
////        return new KafkaTopicConfig();
////    }
//        public NewTopic createNewTopic() {
//            return new NewTopic("order.created", 1, (short) 1);  // name, partitions, replicas
//        }
//}
