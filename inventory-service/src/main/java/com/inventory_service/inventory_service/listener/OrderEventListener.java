package com.inventory_service.inventory_service.listener;

import com.inventory_service.inventory_service.service.InventoryService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderEventListener {
    private final InventoryService inventoryService;

    public OrderEventListener(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @KafkaListener(topics = "order.created", groupId = "inventory-group")
    public void handleOrderCreated(ConsumerRecord<String, String> record) {

            System.out.println("OrderListener is called");
            String msg = record.value();
        try {
            // parse message – e.g. JSON with productId and qty
            JSONObject jsonObject = new JSONObject(msg);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            List<String> productIds = new ArrayList<>();

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject item = itemsArray.getJSONObject(i);
                Long pid = item.getLong("productId");
                Integer qty = item.getInt("quantity");
                inventoryService.decrementStock(pid, qty);
            }
//            if (!jsonObject.has("productId") || !jsonObject.has("qty")) {
//                throw new JSONException("Missing required fields: productId or qty");
//            }
//            Long pid = jsonObject.getLong("product_id");
//            Integer qty = jsonObject.getInt("quantity");
            // For example: {"productId":1, "qty":2}
            // (Parsing code omitted for brevity)
           // inventoryService.decrementStock(pid, qty);
            // Optionally publish an inventory-updated event
        } catch (JSONException e) {
            System.err.println(e.getMessage());
        }
    }
}