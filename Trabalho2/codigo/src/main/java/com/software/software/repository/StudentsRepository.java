package com.software.software.repository;

import com.software.software.models.Students;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * * JpaRepository<Students, Long> significa:
 * 1. Students: É a entidade que este repositório gerencia.
 * 2. Long: É o tipo da Chave Primária (ID) da entidade Students.
 * *  ganha todos os métodos CRUD básicos.
 */

public interface StudentsRepository extends JpaRepository<Students, Long> {

    /**
     * Consulta personalizada para buscar APENAS o e-mail do responsável ('parentEmail')
     * de um aluno específico, baseado no ID do aluno ('studentId').
     * * "SELECT s.parentEmail..." -> Note que não estamos fazendo "SELECT s".
     * Estamos selecionando apenas um atributo. Isso é chamado de "Projeção"
     * e é muito mais eficiente, pois não carrega o objeto 'Students' inteiro
     * do banco de dados, apenas o texto do e-mail.
     */
    @Query("SELECT s.parentEmail FROM Students s WHERE s.studentId = :studentId")
    Optional<String> findEmailParentByStudentId(Long studentId);


    /**
     * - Optional<String>: O tipo de retorno é um 'Optional' contendo uma 'String'.
     * - 'String' -> Porque a consulta pediu apenas o 'parentEmail', que é uma String.
     * - 'Optional' -> É uma boa prática para evitar erros de NullPointerException.
     * Se o 'studentId' não for encontrado (ou se o e-mail for nulo),
     * o método retornará um Optional.empty() em vez de 'null',
     * o que é mais seguro de se trabalhar no código.
     * - (Long studentId): O parâmetro que será usado no lugar de ':studentId' na consulta.
     */
    @Query("SELECT s.parentPhone FROM Students s WHERE s.studentId = :studentId")
    Optional<String> findParentPhoneByStudentId(Long studentId);
}
