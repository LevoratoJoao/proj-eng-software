package com.software.software.services;

import com.software.software.patterns.NotificationFactory;
import org.springframework.stereotype.Component;

/**
 * Factory para criar serviços de notificação
 * Implementa o padrão Factory Method para diferentes tipos de notificação
 */
@Component
public class NotificationServiceFactory {

    /**
     * Cria um serviço de notificação baseado no tipo solicitado
     * Suporta: email, sms, whatsapp
     *
     * @param type Tipo de notificação desejado
     * @return Instância do serviço de notificação correspondente
     * @throws IllegalArgumentException se o tipo não for suportado
     */
    public NotificationFactory createNotificationService(String type) {
        return switch (type.toLowerCase()) {
            case "email" -> new EmailService();       // Serviço de email
            case "sms" -> new SmsService();           // Serviço de SMS
            case "whatsapp" -> new WhatsappService(); // Serviço de WhatsApp
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        };
    }
}
