package com.software.software.services;

import com.software.software.controller.dtos.activity.RequestActivityDto;
import com.software.software.models.Activity;
import com.software.software.models.Students;
import com.software.software.repository.ActivityRepository;
import com.software.software.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final StudentsService studentsService;
    private final StudentsRepository studentsRepository;

    public List<Activity> getOverdueActivities(LocalDate currentDate) {
        return activityRepository.findActivitiesDueTomorrow(currentDate);
    }

    public List<Activity> postActivity(RequestActivityDto dto) {
        List<Students> students = studentsRepository.findAll();
        List<Activity> activities = new ArrayList<>();

        for (Students student : students) {
            Activity activity = dto.toEntity();
            activity.setStudent(student);
            activities.add(activityRepository.save(activity));
        }

        return activities;
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}
