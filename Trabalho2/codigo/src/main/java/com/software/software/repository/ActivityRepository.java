package com.software.software.repository;

import java.time.LocalDate;
import java.util.List;
import com.software.software.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * O Spring Data JPA vai automaticamente criar uma implementação para esta interface.
 * * JpaRepository<Activity, Long> significa:
 * 1. Activity: É a classe de modelo (Entidade) que este repositório vai gerenciar.
 * 2. Long: É o tipo do campo de Chave Primária (o @Id) dentro da classe Activity.
 * * Ao estender JpaRepository, esta interface "ganha" automaticamente
 * vários métodos prontos, como:
 * - save() -> Salva ou atualiza uma atividade
 * - findById() -> Busca uma atividade pelo seu ID
 * - findAll() -> Lista todas as atividades
 * - deleteById() -> Deleta uma atividade pelo seu ID
 */

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    /**
     * Define uma consulta personalizada usando JPQL (Java Persistence Query Language).
     * JPQL é parecido com SQL, mas opera sobre as Entidades (classes Java)
     * em vez de tabelas do banco.
     * * "SELECT a FROM Activity a WHERE a.dueDate = :tomorrow"
     * - "FROM Activity a": Seleciona a entidade Activity (dando a ela o apelido 'a').
     * - "WHERE a.dueDate = :tomorrow": Filtra onde o atributo 'dueDate' da atividade
     * seja igual ao parâmetro que será passado, chamado 'tomorrow'.
     */

    @Query("SELECT a FROM Activity a WHERE a.dueDate = :tomorrow")

    /**
     * Assinatura do método que o Spring vai implementar para nós.
     * * - List<Activity>: O método retornará uma lista de objetos Activity.
     * - findActivitiesDueTomorrow: O nome do método (poderia ser qualquer nome).
     * - (LocalDate tomorrow): O parâmetro que será recebido. O Spring
     * automaticamente associa este parâmetro 'tomorrow' ao ':tomorrow'
     * definido na @Query.
     */

    List<Activity> findActivitiesDueTomorrow(LocalDate tomorrow);

}