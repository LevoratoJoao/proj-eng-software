package com.software.software.services;

import com.software.software.patterns.NotificationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsService extends NotificationFactory {
    private final Logger LOG = LoggerFactory.getLogger(SmsService.class);

    public void sendNotification(String to, String message) {
        LOG.info("SMS sent to: {} with message: {}", to, message);
    }
}
