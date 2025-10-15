package com.order_service.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.order_service.order_service.FallBack.ProductServiceClient;
import com.order_service.order_service.entity.Order;
import com.order_service.order_service.entity.OrderItem;
import com.order_service.order_service.repository.OrderRepository;
//import lombok.RequiredArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class OrderService {


    @Autowired private  OrderRepository orderRepository;
    @Autowired private  KafkaTemplate<String, String> kafkaTemplate;
    @Autowired private  ObjectMapper objectMapper;


    public Order placeOrder(Order order) throws JsonProcessingException {
        order.setOrderDate(LocalDateTime.now());
        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
        }

    order.setItems(order.getItems());
        Order savedOrder = orderRepository.save(order);

        // Publish order event to Kafka
     /*   String orderJson = objectMapper.writeValueAsString(order);
        kafkaTemplate.send("order.created", orderJson);*/

        return savedOrder;
    }

    public Order getOrderById(Long id) {
        return orderRepository.getReferenceById(id);
    }
}
