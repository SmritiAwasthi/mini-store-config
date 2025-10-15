package com.inventory_service.inventory_service.service;

import com.inventory_service.inventory_service.entity.Inventory;
import com.inventory_service.inventory_service.repository.InventoryRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final StringRedisTemplate redisTemplate;
    private final InventoryRepository inventoryRepo;

    public InventoryService(StringRedisTemplate redisTemplate, InventoryRepository inventoryRepo) {
        this.redisTemplate = redisTemplate;
        this.inventoryRepo = inventoryRepo;
    }

    public Integer getStock(Long productId) {
        String v = redisTemplate.opsForValue().get("stock:" + productId);
        if (v == null) return 0;
        return Integer.valueOf(v);
    }

    public void setStock(Long productId, Integer qty) {
        redisTemplate.opsForValue().set("stock:" + productId, qty.toString());
    }

    public void decrementStock(Long productId, Integer qty) {
        // simplistic: atomic decrement

        Long remaining = redisTemplate.opsForValue().decrement("stock:" + productId, qty);
        Inventory inv = inventoryRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Inventory record not found"));

       // inv.setStock(remaining.intValue());
        inv.setStock(inv.getStock()-qty);
        inventoryRepo.save(inv);
    }
}
