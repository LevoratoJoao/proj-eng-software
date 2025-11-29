package com.software.software.services;

import com.software.software.controller.dtos.students.RequestStudentDto;
import com.software.software.controller.dtos.observation.RequestObservationDto; // Import do DTO novo
import com.software.software.models.Observation; // Import da entidade nova
import com.software.software.models.Students;
import com.software.software.services.NotificationServiceFactory;
//import com.software.software.patterns.NotificationServiceFactory; // Import da Factory
import com.software.software.repository.ObservationRepository; // Import do Repositório novo
import com.software.software.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime; // Import para data e hora

/**
 * Serviço responsável pela lógica de negócio dos estudantes
 * Gerencia operações relacionadas aos alunos, dados dos responsáveis e observações
 */
@Service
@RequiredArgsConstructor
public class StudentsService {
    // Mensagens de erro padronizadas
    private final String PARENT_EMAIL_NOT_FOUND = "Parent email not found, ID: ";
    private final String PARENT_PHONE_NOT_FOUND = "Parent phone not found, ID: ";

    // Injeção de dependências (O Lombok cria o construtor para estes campos final)
    private final StudentsRepository studentsRepository;
    private final ObservationRepository observationRepository; // NOVO
    private final NotificationServiceFactory notificationFactory; // NOVO

    /**
     * Busca o email do responsável pelo ID do estudante
     */
    public String getEmailParentByStudentId(Long studentId) {
        return studentsRepository.findEmailParentByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException(PARENT_EMAIL_NOT_FOUND + studentId));
    }

    /**
     * Busca o telefone do responsável pelo ID do estudante
     */
    public String getPhoneParentByStudentId(Long studentId) {
        return studentsRepository.findParentPhoneByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException(PARENT_PHONE_NOT_FOUND + studentId));
    }

    /**
     * Cadastra um novo estudante no sistema
     */
    public Students postStudent(RequestStudentDto dto) {
        Students student = dto.toEntity();
        return studentsRepository.save(student);
    }

    /**
     * Cria uma observação, salva no banco e notifica o responsável
     */
    public void createObservation(Long studentId, RequestObservationDto dto) {
        // 1. Busca o aluno (ou lança erro se não achar)
        Students student = studentsRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found, ID: " + studentId));

        // 2. Cria e Salva a Observação no Banco de Dados
        Observation observation = new Observation();
        observation.setStudent(student);
        observation.setMessage(dto.message());
        observation.setDate(LocalDateTime.now());

        observationRepository.save(observation);

        // 3. Usa o Factory Pattern para criar o serviço de notificação correto (Email, SMS, etc)
        var notificationService = notificationFactory.createNotificationService(dto.notificationType());
        
        // Dispara a notificação para o responsável
        notificationService.sendNotification(student.getParentEmail(), dto.message());
    }
}