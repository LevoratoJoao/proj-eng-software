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
import java.util.List;

/**
 * Serviço responsável pela lógica de negócio das atividades
 * Gerencia operações CRUD e regras específicas de atividades
 */
@Service
@RequiredArgsConstructor
public class ActivityService {

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

    /**
     * Cria uma nova atividade para todos os estudantes cadastrados
     * Implementa a regra de negócio: uma atividade é atribuída a todos os alunos
     */
    public List<Activity> postActivity(RequestActivityDto dto) {
        // Busca todos os estudantes cadastrados
        List<Students> students = studentsRepository.findAll();
        List<Activity> activities = new ArrayList<>();

        // Cria uma atividade para cada estudante
        for (Students student : students) {
            Activity activity = dto.toEntity();
            activity.setStudent(student); // Associa a atividade ao estudante
            activities.add(activityRepository.save(activity));
        }

        return activities;
    }

    /**
     * Retorna todas as atividades cadastradas no sistema
     */
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}
