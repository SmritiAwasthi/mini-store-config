Payment Service (sample)
=======================

This project is a sample Spring Boot microservice implementing a secure PaymentService
with:
- Payment endpoints
- Order validation (HTTP client stub)
- Payment gateway pluggable client (mock impl)
- Kafka producer for order status updates
- JWT-based Bearer authentication (simple symmetric secret)

How to generate a test token (HS256):
- Header: {"alg":"HS256","typ":"JWT"}
- Claims: {"sub":"user-1","iss":"example-issuer","exp": <epoch seconds>}
- Sign using the secret in application.yml (jwt.secret)

Example curl:
curl -X POST http://localhost:8080/api/v1/payments \
 -H "Content-Type: application/json" \
 -H "Authorization: Bearer <JWT_TOKEN>" \
 -H "X-Idempotency-Key: my-key-123" \
 -d '{"orderId":"order-1","userId":"user-1","amount":100.50}'

