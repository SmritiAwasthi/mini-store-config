package com.product_service.product_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.product_service.product_service.entity.Product;
import com.product_service.product_service.service.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAll() {

        System.out.println("ProductController is called");
        return productService.getAllProducts();
    }

//    @GetMapping("/{id}")
//    public Product getById(@PathVariable Long id) {
//        return productService.getProductById(id);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/create")
    public Product create(@RequestBody Product product) throws JsonProcessingException {
        System.out.println("ProductController is called" + product.getDescription());
        return productService.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) throws JsonProcessingException {
        product.setId(id);
        return productService.saveProduct(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}