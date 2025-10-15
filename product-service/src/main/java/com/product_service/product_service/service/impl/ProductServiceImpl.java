package com.product_service.product_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_service.product_service.entity.Product;
import com.product_service.product_service.repository.ProductRepository;
import com.product_service.product_service.service.ProductService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    @Qualifier("productRedisTemplate")
    private RedisTemplate<String, Product> productRedisTemplate;

    @Override
   // @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
//    @PostConstruct
//    public void init() {
//        System.out.println(">>>>> ProductServiceImpl: " + this.getClass());
//    }

    @Override
   // @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {

        System.out.println(">>> DB called for product ID: " + id);

            return productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    }
    @Override
    @Transactional

  //  @CachePut(value = "products", key = "#product.id")
    public Product saveProduct(Product product) throws JsonProcessingException {

        System.out.println("ProductService is called");
        Product savedProduct =productRepository.save(product);
        try {
            String productJson = objectMapper.writeValueAsString(product);
            kafkaTemplate.send("product.created", productJson);
        }
        catch(Exception e){
            System.out.println("Exception in ProductService: "+e.getMessage());
        }
        return product;
    }
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
