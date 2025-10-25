package com.software.software.observers;

import com.software.software.models.Activity;
import com.software.software.services.StudentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParentNotificationObserver implements ActivityObserver {
    private final StudentsService studentsService;

    @Override
    public void onActivityOverdue(Long studentId, Activity activity) {
        studentsService.notifiyParent(studentId, activity);
    }
}