package com.notification_service.notification_service.sevice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendNotification(String toEmail, String subject, String messageBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail); // e.g., "testuser@yopmail.com"
        message.setSubject(subject);
        message.setText(messageBody);
        message.setFrom("smawasthi13@gmail.com"); // Must match spring.mail.username

        mailSender.send(message);
        System.out.println("✅ Email sent to " + toEmail);
    }

}
