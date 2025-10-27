package com.software.software.services;

import com.software.software.controller.dtos.lessons.RequestLessonsDto;
import com.software.software.models.Lessons;
import com.software.software.repository.ActivityRepository;
import com.software.software.repository.LessonsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonsService {
    @Autowired
    private LessonsRepository lessonsRepository;

    public Lessons postLessons(RequestLessonsDto dto) {
        Lessons lesson = dto.toEntity();
        return lessonsRepository.save(lesson);
    }

    public Lessons getLessonsById(Long id) {
        return lessonsRepository.findById(id).orElse(null);
    }
}
