package com.software.software.controller;

import com.software.software.controller.dtos.ApiResponse;
import com.software.software.controller.dtos.activity.RequestActivityDto;
import com.software.software.models.Activity;
import com.software.software.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividade")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    private static final String ACTIVITY_CREATED_SUCCESS = "Activity created successfully";
    private static final String ACTIVITIES_RETRIEVED_SUCCESS = "Activities retrieved successfully";
    private static final String REMINDERS_SENT_SUCCESS = "Reminders sent successfully";

    @PostMapping
    public ResponseEntity<ApiResponse<List<Activity>>> postActivity(@RequestBody RequestActivityDto dto) {
        List<Activity> activities = activityService.postActivity(dto);
        return ResponseEntity.ok(ApiResponse.success(ACTIVITY_CREATED_SUCCESS, activities));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Activity>>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(ApiResponse.success(ACTIVITIES_RETRIEVED_SUCCESS, activities));
    }

    @PostMapping("/reminder/{id}")
    public ResponseEntity<ApiResponse<Void>> sendReminders(@PathVariable Long id) {
        activityService.sendReminderNotifications(id);
        return ResponseEntity.ok(ApiResponse.success(REMINDERS_SENT_SUCCESS));
    }
}
