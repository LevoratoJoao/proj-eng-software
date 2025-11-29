package com.software.software.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity(name = "Observation")
@Table(name = "observations")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime date;

    // Relacionamento com o Aluno (Muitas observações para 1 aluno)
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Students student;
}