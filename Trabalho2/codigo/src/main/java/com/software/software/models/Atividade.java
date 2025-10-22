package com.software.software.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Table(name = "atividade")
@Entity(name = "Atividade")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atividade_id")
    private Long atividadeId;

    @Column(name = "data_entrega", nullable = false)
    private LocalDate dataEntrega;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "bimestre", nullable = false)
    private Integer bimestre;
}
