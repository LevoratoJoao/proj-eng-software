package com.software.software.controller.dtos.students;

import com.software.software.models.Students;

public record RequestStudentDto(
        String name,
        Integer age,
        String parentEmail,
        String parentPhone
) {
    public Students toEntity() {
        Students student = new Students();
        student.setName(this.name);
        student.setAge(this.age);
        student.setParentEmail(this.parentEmail);
        student.setParentPhone(this.parentPhone);
        return student;
    }
}
