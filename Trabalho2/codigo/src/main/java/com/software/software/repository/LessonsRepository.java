package com.software.software.repository;

import com.software.software.models.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * 1. Lessons: É a entidade que este repositório gerencia.
 * 2. Long: É o tipo da Chave Primária (ID) da entidade Lessons.
 * * Esta interface está VAZIA, ela já herda todos os métodos CRUD básicos
 * (save, findById, findAll, delete, count, etc.) do JpaRepository.
 */

public interface LessonsRepository extends JpaRepository<Lessons, Long> {
    List<Lessons> findByGroupClassAndLessonDate(String groupClass, LocalDate lessonDate);
}
