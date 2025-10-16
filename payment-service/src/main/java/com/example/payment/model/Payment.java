package com.example.payment.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments", indexes = {
    @Index(name = "idx_payment_orderid", columnList = "orderId"),
    @Index(name = "idx_payment_idempotency", columnList = "idempotencyKey")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;
    private String userId;

    private BigDecimal amount;

    private String currency = "INR";

    private String status; // PENDING, SUCCESS, FAILED

    private String paymentGatewayTransactionId;

    private String idempotencyKey;

    private Instant createdAt = Instant.now();
    private Instant updatedAt = Instant.now();
}
