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

@Component
public class ActivityTaskScheduler {
    private final Logger LOG = LoggerFactory.getLogger(ActivityTaskScheduler.class);
    private final ActivityService activityService;
    private final ActivityNotificationService notificationService;

    public ActivityTaskScheduler(ActivityService activityService,
            ActivityNotificationService notificationService,
            ParentNotificationObserver parentObserver) {
        this.activityService = activityService;
        this.notificationService = notificationService;
        this.notificationService.addObserver(parentObserver);
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void checkOverdueActivities() {
        LOG.info("Checking for overdue activities... {}", LocalDate.now().plusDays(1));
        List<Activity> overdueActivities = activityService.getOverdueActivities(LocalDate.now().plusDays(1));

        overdueActivities.forEach(activity -> {
            Long studentId = activity.getStudent().getStudentId();
            LOG.info("Notifying observers about overdue activity for student ID: {}", studentId);
            notificationService.notifyOverdueActivity(studentId, activity);
        });
    }
}
