package com.software.software.patterns;

import com.software.software.models.Activity;
import com.software.software.services.NotificationServiceFactory;
import com.software.software.services.StudentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentNotificationObserver implements ActivityObserver {
    private final StudentsService studentsService;
    private final NotificationServiceFactory notificationFactory;

    @Override
    public void onActivityOverdue(Long studentId, Activity activity) {
        String parentEmail = studentsService.getEmailParentByStudentId(studentId);
        String parentPhone = studentsService.getPhoneParentByStudentId(studentId);
        String message = "Your child has an overdue activity: " + activity.getDescription() +
                " which was due on: " + activity.getDueDate();

        notificationFactory.createNotificationService("email").sendNotification(parentEmail, message);
        notificationFactory.createNotificationService("sms").sendNotification(parentPhone, message);
        notificationFactory.createNotificationService("whatsapp").sendNotification(parentPhone, message);
    }
}