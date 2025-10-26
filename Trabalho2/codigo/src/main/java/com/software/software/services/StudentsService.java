package com.software.software.services;

import com.software.software.models.Activity;
import com.software.software.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentsService {
    private final String PARENT_EMAIL_NOT_FOUND = "Parent email not found, ID: ";
    private final String PARENT_PHONE_NOT_FOUND = "Parent phone not found, ID: ";

    private final StudentsRepository studentsRepository;

    public String getEmailParentByStudentId(Long studentId) {
        return studentsRepository.findEmailParentByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException(PARENT_EMAIL_NOT_FOUND + studentId));
    }

    public String getPhoneParentByStudentId(Long studentId) {
        return studentsRepository.findParentPhoneByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException(PARENT_PHONE_NOT_FOUND + studentId));
    }
}
