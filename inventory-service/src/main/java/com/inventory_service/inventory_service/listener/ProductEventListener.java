package com.inventory_service.inventory_service.listener;

import com.inventory_service.inventory_service.entity.Inventory;
import com.inventory_service.inventory_service.repository.InventoryRepository;
import com.inventory_service.inventory_service.service.InventoryService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {

    private final InventoryService inventoryService;
    private final InventoryRepository inventoryRepository;

    public ProductEventListener(InventoryService inventoryService, InventoryRepository inventoryRepository) {
        this.inventoryService = inventoryService;
        this.inventoryRepository = inventoryRepository;
    }


    @KafkaListener(topics = "product.created", groupId = "inventory-group")
    public void handleProductCreated(ConsumerRecord<String, String> record) {

        String msg = record.value();
        try {
            JSONObject jsonObject = new JSONObject(msg);
            Long productId = jsonObject.getLong("id");
            Integer stock = jsonObject.getInt("stock");
            inventoryService.setStock(productId, stock);
            Inventory inv = new Inventory();
            inv.setProductId(productId);
            inv.setStock(stock);
            inventoryRepository.save(inv);

            System.out.println("Inventory initialized for product: " + productId);
        } catch (Exception e) {
            // log error
        }
    }

}