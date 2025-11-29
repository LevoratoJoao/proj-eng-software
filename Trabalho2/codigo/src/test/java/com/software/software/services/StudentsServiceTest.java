package com.software.software.services; 


import com.software.software.controller.dtos.observation.RequestObservationDto; // Caminho do novo DTO
import com.software.software.models.Students;
import com.software.software.patterns.NotificationFactory;
import com.software.software.services.NotificationServiceFactory; 
import com.software.software.repository.ObservationRepository;
import com.software.software.repository.StudentsRepository;

// IMPORTS DO JUNIT E MOCKITO
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

// IMPORTS ESTÁTICOS (Para o when, verify, any funcionar)
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentsServiceTest {

    @InjectMocks
    private StudentsService studentsService;

    @Mock
    private StudentsRepository studentsRepository;

    @Mock
    private ObservationRepository observationRepository;

    @Mock
    private NotificationServiceFactory notificationServiceFactory;

    @Mock
    private NotificationFactory notificationFactory; // O "produto" da factory (ex: EmailService)

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateObservationAndNotifyParent() {
        // CENÁRIO (Arrange)
        Long studentId = 1L;
        // Criando um aluno fictício
        Students mockStudent = new Students(1L, "João", 10, "pais@email.com", "999999999");
        
        // Usando o DTO novo (RequestObservationDto)
        RequestObservationDto dto = new RequestObservationDto("Comportamento exemplar!", "email");

        // Simulando o comportamento dos mocks
        when(studentsRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
        
        // Quando a factory for chamada pedindo "email", retorna nosso mock de notificação
        when(notificationServiceFactory.createNotificationService("email")).thenReturn(notificationFactory);

        // AÇÃO (Act)
        studentsService.createObservation(studentId, dto);

        // VERIFICAÇÃO (Assert)
        // 1. Verifica se salvou a observação no banco
        verify(observationRepository, times(1)).save(any());
        // 2. Verifica se a fábrica foi chamada para criar o serviço de email
        verify(notificationServiceFactory, times(1)).createNotificationService("email");
        // 3. Verifica se a notificação foi enviada
        verify(notificationFactory, times(1)).sendNotification(eq("pais@email.com"), eq("Comportamento exemplar!"));
    }
}