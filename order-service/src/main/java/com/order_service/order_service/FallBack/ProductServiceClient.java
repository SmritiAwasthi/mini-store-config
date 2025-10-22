package com.order_service.order_service.FallBack;

import com.commonFiles.commonFiles.entity.Product;
//import com.product_service.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
//import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ProductServiceClient{
    /*
    * Resilience4j CircuitBreaker changes
    * */

    @Autowired
    private  WebClient webClient;
//   @Autowired
//    private ProductRepository productRepository;

    @Retry(name = "productService", fallbackMethod = "fallbackGetProductById")
   @CircuitBreaker(name = "productService", fallbackMethod = "fallbackGetProductById")
    public Mono<Product> getProductById(Long id){
        System.out.println("retries started" );
        return webClient.get().uri("http://localhost:8886/products/{id}",id).retrieve().
                bodyToMono(Product.class);
    }

    public Mono<Product> fallbackGetProductById(Long id, Throwable ex) {
        System.out.println("⚠️ Fallback triggered for product ID: " + id + ", reason: " + ex.getMessage());
        Product fallbackProduct = new Product();
        fallbackProduct.setId(id);
        fallbackProduct.setName("Fallback Product");
        fallbackProduct.setDescription("This is a fallback product due to service unavailability.");
        fallbackProduct.setPrice(0.0);
        return Mono.just(fallbackProduct);
    }
}
