package com.software.software.services;

import com.software.software.patterns.NotificationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService extends NotificationFactory {
    private final Logger LOG = LoggerFactory.getLogger(WhatsappService.class);

    public void sendNotification(String to, String message) {
        LOG.info("Whatsapp sent to: {} with message: {}", to, message);
    }
}
