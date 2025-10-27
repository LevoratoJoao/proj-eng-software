package com.software.software.repository;

import com.software.software.models.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonsRepository extends JpaRepository<Lessons, Long> {
    
}
