package com.software.software.controller.dtos.lessons;

import com.software.software.models.Activity;
import com.software.software.models.Lessons;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestLessonsDto(
            String discipline ,
            String groupClass,
            LocalDate lessonDate,
            LocalTime timeLessonStart,
            LocalTime timeLessonEnd,
            String theme,
            String objectives
) {
        public Lessons toEntity() {
            Lessons lessons = new Lessons();
            lessons.setDiscipline(this.discipline);
            lessons.setGroupClass(this.groupClass);
            lessons.setLessonDate(this.lessonDate);
            lessons.setTimeLessonStart(this.timeLessonStart);
            lessons.setTimeLessonEnd(this.timeLessonEnd);
            lessons.setTheme(this.theme);
            lessons.setObjectives(this.objectives);
            return lessons;
        }
}
