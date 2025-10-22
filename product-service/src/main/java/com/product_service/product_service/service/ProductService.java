package com.product_service.product_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.commonFiles.commonFiles.entity.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts();
    public Product getProductById(Long id);
    public Product saveProduct(Product product) throws JsonProcessingException;
    public void deleteProduct(Long id);

}
