package com.software.software.repository;

import java.time.LocalDate;
import java.util.List;
import com.software.software.models.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    List<Atividade> findAtividadeByDataEntrega(LocalDate dataEntrega);
}