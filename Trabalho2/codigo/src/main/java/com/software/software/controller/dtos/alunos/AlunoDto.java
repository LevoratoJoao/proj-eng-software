package com.software.software.controller.dtos.alunos;

import com.software.software.models.Alunos;

public record AlunoDto(
    Long id,
    String nome,
    String emailResponsavel,
    String telefoneResponsavel
) {
    public static AlunoDto fromEntity(Alunos aluno) {
        return new AlunoDto(
            aluno.getAlunoId(),
            aluno.getNome(),
            aluno.getEmailResponsavel(),
            aluno.getTelefoneResponsavel()
        );
    }
}
