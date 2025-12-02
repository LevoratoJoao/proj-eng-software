package com.software.software.services;

import com.software.software.controller.dtos.lessons.RequestLessonsDto;
import com.software.software.models.Lessons;
import com.software.software.repository.LessonsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class LessonsService {

    private final LessonsRepository lessonsRepository;

    public LessonsService(LessonsRepository lessonsRepository) {
        this.lessonsRepository = lessonsRepository;
    }

    // Cria e salva um novo registro de aula com validação de horários e prevenção de conflitos
    public Lessons postLessons(RequestLessonsDto dto) {
        if (dto == null) {
            throw new NullPointerException("RequestLessonsDto is null");
        }

        Lessons lesson = dto.toEntity();

        // Validação de horários: início e fim obrigatórios e início antes do fim
        LocalTime start = lesson.getTimeLessonStart();
        LocalTime end = lesson.getTimeLessonEnd();
        if (start == null || end == null) {
            throw new IllegalArgumentException("Horário de início e fim são obrigatórios");
        }
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException("Horário de início deve ser anterior ao horário de fim");
        }

        // Verifica conflitos com outras aulas da mesma turma na mesma data
        if (lesson.getGroupClass() != null && lesson.getLessonDate() != null) {
            List<Lessons> existing = lessonsRepository.findByGroupClassAndLessonDate(
                    lesson.getGroupClass(), lesson.getLessonDate()
            );

            for (Lessons e : existing) {
                LocalTime eStart = e.getTimeLessonStart();
                LocalTime eEnd = e.getTimeLessonEnd();
                if (eStart == null || eEnd == null) {
                    continue; // ignora registros incompletos
                }
                // verifica interseção: start < eEnd && eStart < end
                if (start.isBefore(eEnd) && eStart.isBefore(end)) {
                    throw new IllegalStateException("Horário conflita com outra aula existente");
                }
            }
        }

        return lessonsRepository.save(lesson);
    }

    // Busca uma lição pelo ID
    public Lessons getLessonsById(Long id) {
        if (id == null) {
            return null; // evita chamar o repositório com id nulo
        }
        return lessonsRepository.findById(id).orElse(null);
    }
}
