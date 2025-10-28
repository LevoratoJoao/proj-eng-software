package com.software.software.patterns;

import com.software.software.models.Activity;
import com.software.software.services.NotificationServiceFactory;
import com.software.software.services.StudentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementação do padrão Observer para notificar pais sobre atividades em atraso
 * Esta classe é notificada quando uma atividade está vencida
 */
@Component
@RequiredArgsConstructor
public class ParentNotificationObserver implements ActivityObserver {
    private final StudentsService studentsService;
    private final NotificationServiceFactory notificationFactory;

    /**
     * Método chamado quando uma atividade está em atraso
     * Envia notificações por email, SMS e WhatsApp para os pais
     */
    @Override
    public void onActivityOverdue(Long studentId, Activity activity) {
        // Busca os dados de contato dos pais
        String parentEmail = studentsService.getEmailParentByStudentId(studentId);
        String parentPhone = studentsService.getPhoneParentByStudentId(studentId);

        // Monta a mensagem de notificação
        String message = "Your child has an overdue activity: " + activity.getDescription() +
                " which was due on: " + activity.getDueDate();

        // Envia notificações através de múltiplos canais usando Factory Pattern
        notificationFactory.createNotificationService("email").sendNotification(parentEmail, message);
        notificationFactory.createNotificationService("sms").sendNotification(parentPhone, message);
        notificationFactory.createNotificationService("whatsapp").sendNotification(parentPhone, message);
    }
}