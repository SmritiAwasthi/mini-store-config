package com.api_gateway.Api_server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/products")
    public Mono<ResponseEntity<Map<String, Object>>> productServiceFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Product Service Unavailable");
        response.put("message", "Product service is currently experiencing issues. Please try again later.");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "product-service");
        response.put("status", "CIRCUIT_BREAKER_OPEN");
        
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @GetMapping("/orders")
    public Mono<ResponseEntity<Map<String, Object>>> orderServiceFallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Order Service Unavailable");
        response.put("message", "Order service is currently experiencing issues. Please try again later.");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "order-service");
        response.put("status", "CIRCUIT_BREAKER_OPEN");
        
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response));
    }

    @RequestMapping("/products/**")
    public Mono<ResponseEntity<Map<String, Object>>> productServiceFallbackCatchAll() {
        return productServiceFallback();
    }

    @RequestMapping("/orders/**")
    public Mono<ResponseEntity<Map<String, Object>>> orderServiceFallbackCatchAll() {
        return orderServiceFallback();
    }

    // Health check endpoint for the gateway itself
    @GetMapping("/health")
    public Mono<ResponseEntity<Map<String, Object>>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "api-gateway");
        
        return Mono.just(ResponseEntity.ok(health));
    }
}
