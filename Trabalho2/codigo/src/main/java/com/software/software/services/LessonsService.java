package com.software.software.services;

import com.software.software.controller.dtos.lessons.RequestLessonsDto;
import com.software.software.models.Lessons;
import com.software.software.repository.LessonsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LessonsService {

    // Mensagens padronizadas para exceções, facilitando manutenção
    private static final String MSG_NULL_DTO = "RequestLessonsDto is null";
    private static final String MSG_NULL_ENTITY = "Converted Lessons entity is null";
    private static final String MSG_MISSING_TIMES = "Horário de início e fim são obrigatórios";
    private static final String MSG_INVALID_RANGE = "Horário de início deve ser anterior ao horário de fim";
    private static final String MSG_CONFLICT = "Horário conflita com outra aula existente";

    private final LessonsRepository lessonsRepository;

    // Injeção de dependência via construtor (boa prática, facilita testes)
    public LessonsService(LessonsRepository lessonsRepository) {
        this.lessonsRepository = lessonsRepository;
    }

    /**
     * Cria uma nova lição após validar horários e verificar conflitos.
     * O método é transacional para garantir integridade caso ocorra erro no processo.
     */
    @Transactional
    public Lessons postLessons(RequestLessonsDto dto) {
        // Valida DTO e entidade convertida (evita NPE silencioso)
        Objects.requireNonNull(dto, MSG_NULL_DTO);
        Lessons lesson = Objects.requireNonNull(dto.toEntity(), MSG_NULL_ENTITY);

        // Valida horários obrigatórios e sua consistência
        validateTimes(lesson);

        // Verifica conflitos apenas se turma e data forem fornecidas
        if (lesson.getGroupClass() != null && lesson.getLessonDate() != null) {
            checkConflicts(lesson);
        }

        // Se todas validações passarem, persiste no banco
        return lessonsRepository.save(lesson);
    }

    /**
     * Busca uma lição pelo ID.
     * Retorna null se ID for nulo ou se a lição não existir.
     */
    public Lessons getLessonsById(Long id) {
        if (id == null) {
            return null;
        }
        return lessonsRepository.findById(id).orElse(null);
    }

    /**
     * Valida os horários da lição:
     * - Ambos devem estar preenchidos
     * - Início deve ser antes do fim
     */
    private void validateTimes(Lessons lesson) {
        if (lesson == null) {
            throw new NullPointerException(MSG_NULL_ENTITY);
        }

        LocalTime start = lesson.getTimeLessonStart();
        LocalTime end = lesson.getTimeLessonEnd();

        // Horários obrigatórios
        if (start == null || end == null) {
            throw new IllegalArgumentException(MSG_MISSING_TIMES);
        }

        // Regra de consistência temporal
        if (!start.isBefore(end)) {
            throw new IllegalArgumentException(MSG_INVALID_RANGE);
        }
    }

    /**
     * Verifica se existe outra aula na mesma turma e data
     * com sobreposição de horário.
     */
    private void checkConflicts(Lessons lesson) {
        // Busca aulas no mesmo dia e turma (pode retornar null)
        List<Lessons> existing = Optional.ofNullable(
                lessonsRepository.findByGroupClassAndLessonDate(
                        lesson.getGroupClass(), lesson.getLessonDate()
                )
        ).orElse(Collections.emptyList());

        LocalTime start = lesson.getTimeLessonStart();
        LocalTime end = lesson.getTimeLessonEnd();

        // Avalia cada aula existente
        for (Lessons e : existing) {
            if (e == null) {
                continue;
            }

            LocalTime eStart = e.getTimeLessonStart();
            LocalTime eEnd = e.getTimeLessonEnd();

            // Se a aula existente está incompleta, ignora
            if (eStart == null || eEnd == null) {
                continue;
            }

            // Se houver sobreposição de horários, lança exceção
            if (overlaps(start, end, eStart, eEnd)) {
                throw new IllegalStateException(MSG_CONFLICT);
            }
        }
    }

    /**
     * Verifica se dois intervalos de horário se sobrepõem.
     * Exemplo:
     * 10:00–11:00 sobrepõe 10:30–11:30
     */
    private boolean overlaps(LocalTime start, LocalTime end, LocalTime otherStart, LocalTime otherEnd) {
        return start.isBefore(otherEnd) && otherStart.isBefore(end);
    }
}
