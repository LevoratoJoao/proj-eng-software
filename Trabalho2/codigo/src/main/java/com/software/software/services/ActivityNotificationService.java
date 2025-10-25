package com.software.software.services;

import com.software.software.models.Activity;
import com.software.software.observers.ActivityObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityNotificationService {
    private final List<ActivityObserver> observers = new ArrayList<>();

    public void addObserver(ActivityObserver observer) {
        observers.add(observer);
    }

    public void notifyOverdueActivity(Long studentId, Activity activity) {
        observers.forEach(observer -> observer.onActivityOverdue(studentId, activity));
    }
}