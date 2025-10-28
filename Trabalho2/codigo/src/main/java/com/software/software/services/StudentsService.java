package com.software.software.services;

import com.software.software.controller.dtos.students.RequestStudentDto;
import com.software.software.models.Activity;
import com.software.software.models.Students;
import com.software.software.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela lógica de negócio dos estudantes
 * Gerencia operações relacionadas aos alunos e dados dos responsáveis
 */
@Service
@RequiredArgsConstructor
public class StudentsService {
    // Mensagens de erro padronizadas
    private final String PARENT_EMAIL_NOT_FOUND = "Parent email not found, ID: ";
    private final String PARENT_PHONE_NOT_FOUND = "Parent phone not found, ID: ";

    private final StudentsRepository studentsRepository;

    /**
     * Busca o email do responsável pelo ID do estudante
     * Usado pelo sistema de notificações para enviar alertas aos pais
     */
    public String getEmailParentByStudentId(Long studentId) {
        return studentsRepository.findEmailParentByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException(PARENT_EMAIL_NOT_FOUND + studentId));
    }

    /**
     * Busca o telefone do responsável pelo ID do estudante
     * Usado para envio de SMS e WhatsApp
     */
    public String getPhoneParentByStudentId(Long studentId) {
        return studentsRepository.findParentPhoneByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException(PARENT_PHONE_NOT_FOUND + studentId));
    }

    /**
     * Cadastra um novo estudante no sistema
     * Converte o DTO em entidade e persiste no banco
     */
    public Students postStudent(RequestStudentDto dto) {
        Students student = dto.toEntity();
        return studentsRepository.save(student);
    }
}
