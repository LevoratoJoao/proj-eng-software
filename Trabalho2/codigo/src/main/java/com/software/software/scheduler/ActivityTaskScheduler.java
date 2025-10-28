package com.software.software.scheduler;

import com.software.software.models.Activity;
import com.software.software.patterns.ParentNotificationObserver;
import com.software.software.services.ActivityNotificationService;
import com.software.software.services.ActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Agendador de tarefas para verificar atividades em atraso
 * Executa periodicamente para notificar sobre atividades vencidas
 */
@Component
public class ActivityTaskScheduler {
    private final Logger LOG = LoggerFactory.getLogger(ActivityTaskScheduler.class);
    private final ActivityService activityService;
    private final ActivityNotificationService notificationService;

    /**
     * Construtor que registra o observer para receber notificações
     */
    public ActivityTaskScheduler(ActivityService activityService,
            ActivityNotificationService notificationService,
            ParentNotificationObserver parentObserver) {
        this.activityService = activityService;
        this.notificationService = notificationService;
        this.notificationService.addObserver(parentObserver);
    }

    /**
     * Tarefa agendada que executa a cada 1 minuto
     * Verifica atividades que vencem amanhã e notifica os observadores
     */
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void checkOverdueActivities() {
        LOG.info("Checking for overdue activities... {}", LocalDate.now().plusDays(1));

        // Busca atividades que vencem amanhã
        List<Activity> overdueActivities = activityService.getOverdueActivities(LocalDate.now().plusDays(1));

        // Para cada atividade, notifica os observadores
        overdueActivities.forEach(activity -> {
            Long studentId = activity.getStudent().getStudentId();
            LOG.info("Notifying observers about overdue activity for student ID: {}", studentId);
            notificationService.notifyOverdueActivity(studentId, activity);
        });
    }
}
