package com.order_service.order_service.controller;

import com.order_service.order_service.FallBack.ProductServiceClient;
import com.order_service.order_service.entity.Order;
import com.order_service.order_service.service.OrderService;
//import com.product_service.product_service.entity.Product;
//import com.product_service.product_service.repository.ProductRepository;
import com.commonFiles.commonFiles.entity.Product;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@RequiredArgsConstructor
public class OrderController {


    @Autowired private  OrderService orderService;
//    @Autowired
//    private ProductRepository productRepository;
    @Autowired
    private  ProductServiceClient productServiceClient;

    @PostMapping("/placeOrder")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            order.getItems().forEach(item -> {
                System.out.println("retries order is completed" );
                Product product = productServiceClient.getProductById(Long.parseLong(item.getProductId())).block();//block()--this waits
                if (product == null) {
                    throw new RuntimeException("Product not found with id: " + item.getProductId());
                }
            });
            Order savedOrder = orderService.placeOrder(order);
            System.out.println("Creating order is completed" );
            return ResponseEntity.ok(savedOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Order failed: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        Order orderById = orderService.getOrderById(id);
        return ResponseEntity.of(orderById!=null?java.util.Optional.of(orderById):java.util.Optional.empty());
    }


    @GetMapping("/{id}/exists")
    public boolean checkOrderExists(@PathVariable Long id) {
        return orderService.checkOrderExists(id);
           }
}
