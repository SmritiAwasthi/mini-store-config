package com.notification_service.notification_service.listener;

import com.notification_service.notification_service.sevice.EmailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderNotificationListener {


    @Autowired
    private ChatModel chatModel;
    @Autowired
    private EmailService emailService;




    @KafkaListener(topics = "order.created", groupId = "notification-group")
    public void onOrderCreated(ConsumerRecord<String, String> record) {
        System.out.println("inside Notification service");

        try {
            String msg = record.value();


            String prompt = "Write a friendly and short notification for order: " + msg;

         //   String generated = chatModel.call(new Prompt(prompt)).toString();
            System.out.println("Notify: " + "generated");
//            emailService.sendNotification(
//                    "bibeinnappauwu-6470@yopmail.com",               // Replace with actual recipient
//                    "Order Notification",
//                    "Hi Order created"
//            );
        }
        catch (Exception e){
            throw e;
        }
       // String notification = "Your order is placed: " + msg;
        // send email / push / log

    }
}
