package com.software.software.controller;

import com.software.software.controller.dtos.students.RequestStudentDto;
import com.software.software.models.Students;
import com.software.software.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentsController {

    @Autowired
    private StudentsService studentsService;

    @GetMapping("/{id}/parent-email")
    public ResponseEntity<String> getEmailParent(@PathVariable Long id) {
        return ResponseEntity.ok(studentsService.getEmailParentByStudentId(id));
    }

    @PostMapping
    public ResponseEntity<Students> postStudent(@RequestBody RequestStudentDto dto) {
        Students student = studentsService.postStudent(dto);
        return ResponseEntity.ok(student);
    }
}
