package com.example.payment.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.order-status}")
    private String topic;

    public void publishOrderStatus(String orderId, String status) {
        String payload = String.format("{\"orderId\":\"%s\",\"status\":\"%s\"}", orderId, status);
        kafkaTemplate.send(topic, orderId, payload);
    }
}
