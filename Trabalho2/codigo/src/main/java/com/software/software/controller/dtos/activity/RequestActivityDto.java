package com.software.software.controller.dtos.activity;

import com.software.software.models.Activity;

import java.time.LocalDate;

public record RequestActivityDto(
        LocalDate dueDate,
        String description,
        Integer bimester
) {
    public Activity toEntity() {
        Activity activity = new Activity();
        activity.setDueDate(this.dueDate);
        activity.setDescription(this.description);
        activity.setBimester(this.bimester);
        return activity;
    }
}
