package com.example.payment.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OrderServiceClient {
    private final WebClient webClient = WebClient.create("http://orders-service:8080");

    public boolean orderExists(String orderId) {
        try {
            Mono<Boolean> mono = webClient.get()
                    .uri("/orders/{id}/exists", orderId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorReturn(false);
            return mono.block();
        } catch (Exception e) {
            return false;
        }
    }
}
