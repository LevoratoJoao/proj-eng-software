package com.software.software.controller.dtos.students;

import com.software.software.models.Students;

public record StudentDto(
    Long id,
    String name,
    String parentEmail,
    String parentPhone
) {
    public static StudentDto fromEntity(Students student) {
        return new StudentDto(
                student.getStudentId(),
                student.getName(),
                student.getParentEmail(),
                student.getParentPhone()
        );
    }
}
