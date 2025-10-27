package com.software.software.controller;

import com.software.software.controller.dtos.lessons.RequestLessonsDto;
import com.software.software.models.Lessons;
import com.software.software.services.ActivityService;
import com.software.software.services.LessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonsController {
    @Autowired
    private LessonsService lessonsService;

    @PostMapping
    public ResponseEntity<Lessons> postLessons(@RequestBody RequestLessonsDto dto) {
        Lessons lesson = lessonsService.postLessons(dto);
        return ResponseEntity.ok(lesson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lessons> getLessonsById(@PathVariable Long id) {
        Lessons lesson = lessonsService.getLessonsById(id);
        return ResponseEntity.ok(lesson);
    }
}
