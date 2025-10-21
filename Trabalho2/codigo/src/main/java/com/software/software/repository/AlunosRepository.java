package com.software.software.repository;

import com.software.software.models.Alunos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunosRepository extends JpaRepository<Alunos, Long> {

    Optional<String> findEmailResponsavelByAlunoId(Long alunoId);
}
