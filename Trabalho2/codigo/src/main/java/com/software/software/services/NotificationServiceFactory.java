package com.software.software.services;

import com.software.software.patterns.NotificationFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificationServiceFactory {

    public NotificationFactory createNotificationService(String type) {
        return switch (type.toLowerCase()) {
            case "email" -> new EmailService();
            case "sms" -> new SmsService();
            case "whatsapp" -> new WhatsappService();
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        };
    }
}