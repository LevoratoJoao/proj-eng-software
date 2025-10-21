package com.software.software.controller.dtos.alunos;

public record AlunoRequestDto(
    String nome,
    String emailResponsavel,
    String telefoneResponsavel
) {
}
