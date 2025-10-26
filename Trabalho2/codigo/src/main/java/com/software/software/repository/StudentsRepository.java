package com.software.software.repository;

import com.software.software.models.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentsRepository extends JpaRepository<Students, Long> {
    @Query("SELECT s.parentEmail FROM Students s WHERE s.studentId = :studentId")
    Optional<String> findEmailParentByStudentId(Long studentId);

    @Query("SELECT s.parentPhone FROM Students s WHERE s.studentId = :studentId")
    Optional<String> findParentPhoneByStudentId(Long studentId);
}
