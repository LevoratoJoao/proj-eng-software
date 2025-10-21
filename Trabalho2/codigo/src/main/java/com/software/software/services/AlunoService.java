package com.software.software.services;

import com.software.software.controller.dtos.alunos.AlunoDto;
import com.software.software.repository.AlunosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunosRepository alunosRepository;

    public String getEmailResponsavelByAlunoId(Long alunoId) {
        return alunosRepository.findEmailResponsavelByAlunoId(alunoId)
                .orElseThrow(() -> new RuntimeException("Email do responsável não encontrado para o aluno ID: " + alunoId));
    }
}
