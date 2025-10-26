package com.software.software.controller;

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

    @PostMapping
    public ResponseEntity<List<Activity>> postActivity(@RequestBody RequestActivityDto dto) {
        List<Activity> activities = activityService.postActivity(dto);
        return ResponseEntity.ok(activities);
    }

    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        List<Activity> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }
}
