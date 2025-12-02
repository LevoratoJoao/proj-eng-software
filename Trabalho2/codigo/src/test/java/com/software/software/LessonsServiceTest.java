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
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonsServiceTest {

    @Mock
    private LessonsRepository lessonsRepository;

    @Mock
    private RequestLessonsDto dto;

    @InjectMocks
    private LessonsService lessonsService;

    @Test
    void postLessonsSuccess() {
        Lessons lesson = new Lessons();
        lesson.setLessonId(1L);
        lesson.setDiscipline("Math");
        lesson.setGroupClass("A1");
        lesson.setLessonDate(LocalDate.now());
        lesson.setTimeLessonStart(LocalTime.of(10, 0));
        lesson.setTimeLessonEnd(LocalTime.of(11, 0));
        lesson.setTheme("Fractions");
        lesson.setObjectives("Understand basic fractions");

        when(dto.toEntity()).thenReturn(lesson);
        when(lessonsRepository.save(any(Lessons.class))).thenReturn(lesson);

        Lessons result = lessonsService.postLessons(dto);

        assertNotNull(result);
        assertEquals(1L, result.getLessonId());
        assertEquals("Math", result.getDiscipline());
        verify(dto).toEntity();
        verify(lessonsRepository).save(lesson);
    }

    @Test
    void postLessonsRepositoryThrows() {
        Lessons validLesson = new Lessons();
        validLesson.setGroupClass("A1");
        validLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        validLesson.setTimeLessonStart(LocalTime.of(10, 0));
        validLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(validLesson);
        when(lessonsRepository.save(any())).thenThrow(new RuntimeException("DB Error"));

        assertThrows(RuntimeException.class, () -> lessonsService.postLessons(dto));
        verify(lessonsRepository).save(any(Lessons.class));
    }

    @Test
    void postLessonsNullDtoThrowsNPE() {
        assertThrows(NullPointerException.class, () -> lessonsService.postLessons(null));
        verify(lessonsRepository, never()).save(any());
    }

    @Test
    void getLessonsByIdFound() {
        Lessons lesson = new Lessons();
        lesson.setLessonId(10L);

        when(lessonsRepository.findById(10L)).thenReturn(Optional.of(lesson));

        Lessons result = lessonsService.getLessonsById(10L);

        assertNotNull(result);
        assertEquals(10L, result.getLessonId());
        verify(lessonsRepository).findById(10L);
    }

    @Test
    void getLessonsByIdNotFound() {
        when(lessonsRepository.findById(2L)).thenReturn(Optional.empty());

        Lessons result = lessonsService.getLessonsById(2L);

        assertNull(result);
        verify(lessonsRepository).findById(2L);
    }

    @Test
    void getLessonsByIdNullId() {
        Lessons result = lessonsService.getLessonsById(null);

        assertNull(result);
        verify(lessonsRepository, never()).findById(any());
    }

    @Test
    void postLessonsConflictThrows() {
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(newLesson);

        Lessons existing = new Lessons();
        existing.setGroupClass("A1");
        existing.setLessonDate(LocalDate.of(2025, 1, 1));
        existing.setTimeLessonStart(LocalTime.of(10, 30));
        existing.setTimeLessonEnd(LocalTime.of(11, 30));

        when(lessonsRepository.findByGroupClassAndLessonDate("A1", newLesson.getLessonDate()))
                .thenReturn(Arrays.asList(existing));

        assertThrows(IllegalStateException.class, () -> lessonsService.postLessons(dto));
        verify(lessonsRepository, never()).save(any());
        verify(lessonsRepository).findByGroupClassAndLessonDate("A1", newLesson.getLessonDate());
    }

    @Test
    void postLessonsNoConflictBeforeAllowsSave() {
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(newLesson);

        Lessons existing = new Lessons();
        existing.setGroupClass("A1");
        existing.setLessonDate(LocalDate.of(2025, 1, 1));
        existing.setTimeLessonStart(LocalTime.of(8, 0));
        existing.setTimeLessonEnd(LocalTime.of(9, 0));

        when(lessonsRepository.findByGroupClassAndLessonDate("A1", newLesson.getLessonDate()))
                .thenReturn(Arrays.asList(existing));
        when(lessonsRepository.save(any(Lessons.class))).thenReturn(newLesson);

        Lessons result = lessonsService.postLessons(dto);

        assertNotNull(result);
        verify(lessonsRepository).save(newLesson);
        verify(lessonsRepository).findByGroupClassAndLessonDate("A1", newLesson.getLessonDate());
    }

    @Test
    void postLessonsNoConflictAfterAllowsSave() {
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(newLesson);

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
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(newLesson);

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
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(10, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(10, 0));

        when(dto.toEntity()).thenReturn(newLesson);

        assertThrows(IllegalArgumentException.class, () -> lessonsService.postLessons(dto));
        verify(lessonsRepository, never()).findByGroupClassAndLessonDate(anyString(), any());
        verify(lessonsRepository, never()).save(any());
    }

    @Test
    void postLessonsInvalidDateFormatThrows() {
        // Teste 1: simula DTO que falha ao converter data (DateTimeParseException)
        when(dto.toEntity()).thenThrow(new DateTimeParseException("Invalid date", "bad", 0));

        assertThrows(DateTimeParseException.class, () -> lessonsService.postLessons(dto));
        verify(lessonsRepository, never()).save(any());
        verify(lessonsRepository, never()).findByGroupClassAndLessonDate(anyString(), any());
    }

    @Test
    void postLessonsMissingRequiredFieldThrows() {
        // Teste 2: falta de campo obrigatório (ex.: início nulo)
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(null); // campo obrigatório ausente
        newLesson.setTimeLessonEnd(LocalTime.of(11, 0));

        when(dto.toEntity()).thenReturn(newLesson);

        assertThrows(IllegalArgumentException.class, () -> lessonsService.postLessons(dto));
        verify(lessonsRepository, never()).findByGroupClassAndLessonDate(anyString(), any());
        verify(lessonsRepository, never()).save(any());
    }

    @Test
    void postLessonsInvertedTimeThrows() {
        // Teste 3: horário invertido (fim antes do início)
        Lessons newLesson = new Lessons();
        newLesson.setGroupClass("A1");
        newLesson.setLessonDate(LocalDate.of(2025, 1, 1));
        newLesson.setTimeLessonStart(LocalTime.of(11, 0));
        newLesson.setTimeLessonEnd(LocalTime.of(10, 0)); // fim antes do início

        when(dto.toEntity()).thenReturn(newLesson);

        assertThrows(IllegalArgumentException.class, () -> lessonsService.postLessons(dto));
        verify(lessonsRepository, never()).findByGroupClassAndLessonDate(anyString(), any());
        verify(lessonsRepository, never()).save(any());
    }
}
