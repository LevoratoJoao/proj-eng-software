package com.software.software.services;

import com.software.software.scheduler.ActivityTaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final Logger LOG = LoggerFactory.getLogger(ActivityTaskScheduler.class);

    public void sendNotification(String to, String message) {
        LOG.info("Email sent to: {} with message: {}", to, message);
    }
}
