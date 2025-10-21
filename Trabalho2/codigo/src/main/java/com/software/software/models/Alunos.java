package com.software.software.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "alunos")
@Entity(name = "Alunos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Alunos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aluno_id")
    private Long alunoId;

    @Column(name = "nome", nullable = false)
    private String nome;
    @Column(name = "idade", nullable = false)
    private Integer idade;
    @Column(name = "email_responsavel", nullable = false)
    private String emailResponsavel;
    @Column(name = "telefone_responsavel", nullable = false)
    private String telefoneResponsavel;

}
