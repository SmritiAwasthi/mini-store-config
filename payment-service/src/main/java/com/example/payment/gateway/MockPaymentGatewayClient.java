package com.example.payment.gateway;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Component
public class MockPaymentGatewayClient implements PaymentGatewayClient {
    @Override
    public GatewayResult charge(String orderId, String userId, BigDecimal amount, Map<String, String> metadata) {
        String tx = "mock-" + UUID.randomUUID();
        return new GatewayResult(true, tx, "{\"status\":\"ok\"}");
    }
}
