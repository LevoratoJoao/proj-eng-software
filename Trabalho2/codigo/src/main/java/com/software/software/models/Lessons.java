package com.software.software.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "lessons")
@Entity(name = "Lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Lessons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "discipline", nullable = false)
    private String discipline;

    @Column(name = "group_class", nullable = false)
    private String groupClass;

    @Column(name = "lesson_date", nullable = false)
    private LocalDate lessonDate;

    @Column(name = "lesson_time_start", nullable = false)
    private LocalTime timeLessonStart;

    @Column(name = "lesson_time_end", nullable = false)
    private LocalTime timeLessonEnd;

    @Column(name = "theme", nullable = false)
    private String theme;

    @Column(name = "objectives", nullable = false)
    private String objectives;

}
