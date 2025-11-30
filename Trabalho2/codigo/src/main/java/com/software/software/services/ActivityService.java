package com.software.software.services;

import com.software.software.controller.dtos.activity.RequestActivityDto;
import com.software.software.models.Activity;
import com.software.software.models.Students;
import com.software.software.repository.ActivityRepository;
import com.software.software.repository.StudentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serviço responsável pela lógica de negócio das atividades
 * Gerencia operações CRUD e regras específicas de atividades
 */
@Service
@RequiredArgsConstructor
public class ActivityService {
    private static final String ERROR_MISSING_FIELDS = "Due date, description, and priority must be provided.";
    private static final String ERROR_DUE_DATE_PAST = "Due date cannot be in the past.";
    private static final String ERROR_BIMESTER_RANGE = "Bimester must be between 1 and 4.";
    private static final String ERROR_NO_STUDENTS = "No students found to assign activities.";

    private final ActivityRepository activityRepository;
    private final StudentsService studentsService;
    private final StudentsRepository studentsRepository;

    /**
     * Busca atividades que vencem em uma data específica
     * Usado pelo agendador para verificar atividades em atraso
     */
    public List<Activity> getOverdueActivities(LocalDate currentDate) {
        return activityRepository.findActivitiesDueTomorrow(currentDate);
    }

    public void verifyActivityData(RequestActivityDto dto) {
        if (dto.dueDate() == null || dto.description() == null || dto.bimester() == null) {
            throw new IllegalArgumentException(ERROR_MISSING_FIELDS);
        }

        if (dto.dueDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(ERROR_DUE_DATE_PAST);
        }

        if (dto.bimester() < 1 || dto.bimester() > 4) {
            throw new IllegalArgumentException(ERROR_BIMESTER_RANGE);
        }
    }

    /**
     * Cria uma nova atividade para todos os estudantes cadastrados
     * Implementa a regra de negócio: uma atividade é atribuída a todos os alunos
     */
    public Map<String, Object> postActivity(RequestActivityDto dto) {
        Map<String, Object> response = new HashMap<>();

        verifyActivityData(dto);

        // Busca todos os estudantes cadastrados
        List<Students> students = studentsRepository.findAll();
        if (students.isEmpty()) {
            throw new RuntimeException(ERROR_NO_STUDENTS);
        }
        List<Activity> activities = new ArrayList<>();

        // Cria uma atividade para cada estudante
        for (Students student : students) {
            Activity activity = dto.toEntity();
            activity.setStudent(student); // Associa a atividade ao estudante
            activities.add(activityRepository.save(activity));
        }
        response.put("message", "Activities created and assigned to all students.");

        response.put("activities", activities);

        return response;
    }

    /**
     * Retorna todas as atividades cadastradas no sistema
     */
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /**
     * Envia notificações de lembrete para os responsáveis sobre uma atividade específica
     * Utiliza o padrão Factory para criar serviços de notificação diferentes
     */
    public void sendReminderNotifications(Long id) {
        activityRepository.findById(id).ifPresentOrElse(activity -> {
            String emailParent = studentsService.getEmailParentByStudentId(activity.getStudent().getStudentId());
            String phoneParent = studentsService.getPhoneParentByStudentId(activity.getStudent().getStudentId());

            String message = "Reminder: Your child has an upcoming activity: " + activity.getDescription() +
                    " due on: " + activity.getDueDate();

            NotificationServiceFactory notificationFactory = new NotificationServiceFactory();
            notificationFactory.createNotificationService("email").sendNotification(emailParent, message);
            notificationFactory.createNotificationService("sms").sendNotification(phoneParent, message);
            notificationFactory.createNotificationService("whatsapp").sendNotification(phoneParent, message);
        }, () -> {
            throw new RuntimeException("Activity not found, ID: " + id);
        });

    }
}
