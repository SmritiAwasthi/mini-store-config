package com.example.payment.service;

import com.example.payment.client.OrderServiceClient;
import com.example.payment.dto.PaymentRequest;
import com.example.payment.dto.PaymentResponse;
import com.example.payment.gateway.PaymentGatewayClient;
import com.example.payment.kafka.OrderStatusProducer;
import com.example.payment.model.Payment;
import com.example.payment.repo.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayClient gatewayClient;
    private final OrderServiceClient orderServiceClient;
    private final OrderStatusProducer orderStatusProducer;

    @Override
    @Transactional
    public PaymentResponse createPayment(com.example.payment.dto.PaymentRequest request, String idempotencyKey) {
        if (idempotencyKey != null) {
            var opt = paymentRepository.findByIdempotencyKey(idempotencyKey);
            if (opt.isPresent()) {
                var p = opt.get();
                return PaymentResponse.builder()
                        .paymentId(p.getId().toString())
                        .status(p.getStatus())
                        .message("idempotent")
                        .build();
            }
        }

        boolean exists = orderServiceClient.orderExists(request.getOrderId());
        if (!exists) {
            return PaymentResponse.builder().status("FAILED").message("Order not found").build();
        }

        Payment payment = Payment.builder()
                .orderId(request.getOrderId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .status("PENDING")
                .idempotencyKey(idempotencyKey)
                .build();
        payment = paymentRepository.save(payment);

        var result = gatewayClient.charge(request.getOrderId(), request.getUserId(), request.getAmount(), new HashMap<>());

        if (result.success) {
            payment.setStatus("SUCCESS");
            payment.setPaymentGatewayTransactionId(result.transactionId);
            paymentRepository.save(payment);
            orderStatusProducer.publishOrderStatus(request.getOrderId(), "PAID");
            return PaymentResponse.builder()
                    .paymentId(payment.getId().toString())
                    .status("SUCCESS")
                    .message("Payment successful")
                    .build();
        } else {
            payment.setStatus("FAILED");
            paymentRepository.save(payment);
            orderStatusProducer.publishOrderStatus(request.getOrderId(), "PAYMENT_FAILED");
            return PaymentResponse.builder()
                    .paymentId(payment.getId().toString())
                    .status("FAILED")
                    .message("Payment gateway failure")
                    .build();
        }
    }
}
