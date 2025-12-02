package com.software.software;

import com.software.software.controller.dtos.lessons.RequestLessonsDto;
import com.software.software.models.Lessons;
import com.software.software.repository.LessonsRepository;
import com.software.software.services.LessonsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonsServiceTest {

    // Mock do repositório usado pelo serviço
    @Mock
    private LessonsRepository lessonsRepository;

    // Mock do DTO de requisição convertido para entidade nas chamadas
    @Mock
    private RequestLessonsDto dto;

    // Instância do serviço com mocks injetados
    @InjectMocks
    private LessonsService lessonsService;

    @Test
    void postLessonsSuccess() {
        // Objetivo: validar criação/salvamento quando DTO é válido
        Lessons lesson = new Lessons();
        lesson.setLessonId(1L);
        lesson.setDiscipline("Math");
        lesson.setGroupClass("A1");
        lesson.setLessonDate(LocalDate.now());
        lesson.setTimeLessonStart(LocalTime.of(10, 0));
        lesson.setTimeLessonEnd(LocalTime.of(11, 0));
        lesson.setTheme("Fractions");
        lesson.setObjectives("Understand basic fractions");

        // Setup: dto converte para entidade e repositório retorna a mesma ao salvar
        when(dto.toEntity()).thenReturn(lesson);
        when(lessonsRepository.save(any(Lessons.class))).thenReturn(lesson);

        // Execução
        Lessons result = lessonsService.postLessons(dto);

        // Verificações: resultado válido e métodos chamados
        assertNotNull(result);
        assertEquals(1L, result.getLessonId());
        assertEquals("Math", result.getDiscipline());
        verify(dto).toEntity();
        verify(lessonsRepository).save(lesson);
    }

    @Test
    void postLessonsRepositoryThrows() {
        // Objetivo: garantir que exceções do repositório são propagadas
        Lessons validLesson = new Lessons();
        validLesson.setGroupClass("A1");
        validLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        validLesson.setTimeLessonStart(LocalTime.of(10, 0));
        validLesson.setTimeLessonEnd(LocalTime.of(11, 0));
        when(dto.toEntity()).thenReturn(validLesson);

        // Agora o save será chamado e a exceção do repositório será usada
        when(lessonsRepository.save(any())).thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class, () -> lessonsService.postLessons(dto));
        verify(lessonsRepository).save(any(Lessons.class));
    }

    @Test
    void postLessonsNullDtoThrowsNPE() {
        // Objetivo: comportamento quando DTO é nulo — evita salvar
        assertThrows(NullPointerException.class, () -> lessonsService.postLessons(null));
        verify(lessonsRepository, never()).save(any());
    }

    @Test
    void getLessonsByIdFound() {
        // Objetivo: recuperar lição existente por ID
        Lessons lesson = new Lessons();
        lesson.setLessonId(10L);

        when(lessonsRepository.findById(10L)).thenReturn(Optional.of(lesson));

        Lessons result = lessonsService.getLessonsById(10L);

        // Verifica retorno e chamada ao repositório
        assertNotNull(result);
        assertEquals(10L, result.getLessonId());
        verify(lessonsRepository).findById(10L);
    }

    @Test
    void getLessonsByIdNotFound() {
        // Objetivo: quando ID não existe, retorna null
        when(lessonsRepository.findById(2L)).thenReturn(Optional.empty());

        Lessons result = lessonsService.getLessonsById(2L);

        assertNull(result);
        verify(lessonsRepository).findById(2L);
    }

    @Test
    void getLessonsByIdNullId() {
        // Objetivo: se ID nulo, não chamar repositório e retornar null
        Lessons result = lessonsService.getLessonsById(null);

        assertNull(result);
        verify(lessonsRepository, never()).findById(any());
    }

    @Test
    void postLessonsConflictThrows() {
        // Objetivo: bloquear criação quando há conflito de horário com mesma turma/data
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(newLesson);

        // Lição existente que intersecta o intervalo (10:30-11:30) -> conflito
        Lessons existing = new Lessons();
        existing.setGroupClass("A1");
        existing.setLessonDate(LocalDate.of(2025, 1, 1));
        existing.setTimeLessonStart(LocalTime.of(10, 30));
        existing.setTimeLessonEnd(LocalTime.of(11, 30));

        when(lessonsRepository.findByGroupClassAndLessonDate("A1", newLesson.getLessonDate()))
                .thenReturn(Arrays.asList(existing));

        // Expectativa: IllegalStateException e nenhum save executado
        assertThrows(IllegalStateException.class, () -> lessonsService.postLessons(dto));
        verify(lessonsRepository, never()).save(any());
        verify(lessonsRepository).findByGroupClassAndLessonDate("A1", newLesson.getLessonDate());
    }

    @Test
    void postLessonsNoConflictBeforeAllowsSave() {
        // Objetivo: permitir salvar quando lição existente termina antes do novo início
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(newLesson);

        // Existente: 08:00-09:00
        Lessons existing = new Lessons();
        existing.setGroupClass("A1");
        existing.setLessonDate(LocalDate.of(2025, 1, 1));
        existing.setTimeLessonStart(LocalTime.of(8, 0));
        existing.setTimeLessonEnd(LocalTime.of(9, 0));

        when(lessonsRepository.findByGroupClassAndLessonDate("A1", newLesson.getLessonDate()))
                .thenReturn(Arrays.asList(existing));
        when(lessonsRepository.save(any(Lessons.class))).thenReturn(newLesson);

        Lessons result = lessonsService.postLessons(dto);

        // Verifica que salvou e consultou o repositório
        assertNotNull(result);
        verify(lessonsRepository).save(newLesson);
        verify(lessonsRepository).findByGroupClassAndLessonDate("A1", newLesson.getLessonDate());
    }

    @Test
    void postLessonsNoConflictAfterAllowsSave() {
        // Objetivo: permitir salvar quando lição existente começa depois do novo fim
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(newLesson);

        // Existente: 12:00-13:00
        Lessons existing = new Lessons();
        existing.setGroupClass("A1");
        existing.setLessonDate(LocalDate.of(2025, 1, 1));
        existing.setTimeLessonStart(LocalTime.of(12, 0));
        existing.setTimeLessonEnd(LocalTime.of(13, 0));

        when(lessonsRepository.findByGroupClassAndLessonDate("A1", newLesson.getLessonDate()))
                .thenReturn(Arrays.asList(existing));
        when(lessonsRepository.save(any(Lessons.class))).thenReturn(newLesson);

        Lessons result = lessonsService.postLessons(dto);

        assertNotNull(result);
        verify(lessonsRepository).save(newLesson);
        verify(lessonsRepository).findByGroupClassAndLessonDate("A1", newLesson.getLessonDate());
    }

    @Test
    void postLessonsExistingIncompleteIgnoredAllowsSave() {
        // Objetivo: ignorar registros existentes com horários incompletos e permitir salvar
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(newLesson);

        // Existente com horários nulos (registro incompleto) -> deve ser ignorado
        Lessons existing = new Lessons();
        existing.setGroupClass("A1");
        existing.setLessonDate(LocalDate.of(2025, 1, 1));
        existing.setTimeLessonStart(null);
        existing.setTimeLessonEnd(null);

        when(lessonsRepository.findByGroupClassAndLessonDate("A1", newLesson.getLessonDate()))
                .thenReturn(Collections.singletonList(existing));
        when(lessonsRepository.save(any(Lessons.class))).thenReturn(newLesson);

        Lessons result = lessonsService.postLessons(dto);

        assertNotNull(result);
        verify(lessonsRepository).save(newLesson);
    }

    @Test
    void postLessonsInvalidTimesThrowsIllegalArgumentException() {
        // Objetivo: validar que início >= fim é inválido
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(10, 0));

        when(dto.toEntity()).thenReturn(newLesson);

        // Expectativa: validação bate antes de consultar conflitos e salvar
        assertThrows(IllegalArgumentException.class, () -> lessonsService.postLessons(dto));
        verify(lessonsRepository, never()).findByGroupClassAndLessonDate(anyString(), any());
        verify(lessonsRepository, never()).save(any());
    }
}
