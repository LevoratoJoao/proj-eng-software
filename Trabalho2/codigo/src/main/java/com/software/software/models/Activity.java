package com.software.software.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "activity")
@Entity(name = "Activity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "bimester", nullable = false)
    private Integer bimester;
}
