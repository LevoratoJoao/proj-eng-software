package com.software.software;

import com.software.software.controller.dtos.activity.RequestActivityDto;
import com.software.software.models.Activity;
import com.software.software.models.Students;
import com.software.software.repository.ActivityRepository;
import com.software.software.repository.StudentsRepository;
import com.software.software.services.ActivityService;
import com.software.software.services.StudentsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;
    
    @Mock
    private StudentsRepository studentsRepository;
    
    @Mock
    private StudentsService studentsService;
    
    @InjectMocks
    private ActivityService activityService;

    @Test
    void postActivityWithStudentsCreatesActivitiesForAllStudents() {
        Students student1 = new Students();
        student1.setStudentId(1L);
        Students student2 = new Students();
        student2.setStudentId(2L);
        
        // Mock o retorno do repositório de estudantes com dois estudantes
        when(studentsRepository.findAll()).thenReturn(Arrays.asList(student1, student2));
        when(activityRepository.save(any(Activity.class))).thenReturn(new Activity());
        
        RequestActivityDto dto = new RequestActivityDto(
                LocalDate.now().plusDays(7),
                "Test Activity",
                1
        );

        Map<String, Object> result = activityService.postActivity(dto);

        // Verifica se o método save foi chamado duas vezes (uma para cada estudante)
        verify(activityRepository, times(2)).save(any(Activity.class));
        // Verifica a mensagem e a lista de atividades retornadas
        assertEquals("Activities created and assigned to all students.", result.get("message"));
        assertNotNull(result.get("activities"));
    }

    @Test
    void getAllActivitiesReturnsAllActivities() {
        List<Activity> expectedActivities = Arrays.asList(new Activity(), new Activity());
        // Mock o retorno do repositório de atividades
        when(activityRepository.findAll()).thenReturn(expectedActivities);

        List<Activity> result = activityService.getAllActivities();

        // Verifica se a lista retornada é igual à esperada
        assertEquals(expectedActivities, result);
        verify(activityRepository).findAll();
    }

    @Test
    void sendReminderNotificationsWithValidIdSendsNotifications() {
        Activity activity = new Activity();
        activity.setDescription("Test Activity");
        activity.setDueDate(LocalDate.now());
        Students student = new Students();
        student.setStudentId(1L);
        activity.setStudent(student);

        // Mock o retorno do repositório de atividades e do serviço de estudantes
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));
        when(studentsService.getEmailParentByStudentId(1L)).thenReturn("parent@test.com");
        when(studentsService.getPhoneParentByStudentId(1L)).thenReturn("123456789");

        // Verifica se o método executa sem lançar exceções
        assertDoesNotThrow(() -> activityService.sendReminderNotifications(1L));
        verify(activityRepository).findById(1L);
    }

    @Test
    void postActivityWithNoStudentsReturnsWarningMessage() {
        when(studentsRepository.findAll()).thenReturn(Arrays.asList());

        RequestActivityDto dto = new RequestActivityDto(
                LocalDate.now().plusDays(7),
                "Test Activity",
                1
        );
        // Verifica se a exceção é lançada quando não há estudantes
        assertThrows(RuntimeException.class, () -> activityService.postActivity(dto));
    }

    @Test
    void postActivityWithNullDescriptionThrowsException() {
        RequestActivityDto dto = new RequestActivityDto(
                LocalDate.now().plusDays(7),
                null,
                1
        );
        // Verifica se a exceção é lançada quando a descrição é nula
        assertThrows(RuntimeException.class, () -> activityService.postActivity(dto));
    }

    @Test
    void postActivityWithPastDueDateThrowsException() {
        RequestActivityDto dto = new RequestActivityDto(
                LocalDate.now().minusDays(1),
                "Test Activity",
                1
        );
        // Verifica se a exceção é lançada quando a data de vencimento é passada
        assertThrows(RuntimeException.class, () -> activityService.postActivity(dto));
    }

    @Test
    void sendReminderNotificationsWithInvalidIdThrowsException() {
        when(activityRepository.findById(999L)).thenReturn(Optional.empty());
        // Verifica se a exceção é lançada quando o ID da atividade é inválido
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> activityService.sendReminderNotifications(999L));
        assertEquals("Activity not found, ID: 999", exception.getMessage());
    }

    @Test
    void sendReminderNotificationsWithNullIdThrowsException() {
        // Verifica se a exceção é lançada quando o ID da atividade é nulo
        assertThrows(RuntimeException.class,
                () -> activityService.sendReminderNotifications(null));
    }
}
