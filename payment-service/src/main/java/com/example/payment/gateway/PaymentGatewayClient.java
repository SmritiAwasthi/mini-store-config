package com.example.payment.gateway;

import java.math.BigDecimal;
import java.util.Map;

public interface PaymentGatewayClient {
    GatewayResult charge(String orderId, String userId, BigDecimal amount, Map<String,String> metadata);

    class GatewayResult {
        public final boolean success;
        public final String transactionId;
        public final String rawResponse;

        public GatewayResult(boolean success, String transactionId, String rawResponse) {
            this.success = success;
            this.transactionId = transactionId;
            this.rawResponse = rawResponse;
        }
    }
}
