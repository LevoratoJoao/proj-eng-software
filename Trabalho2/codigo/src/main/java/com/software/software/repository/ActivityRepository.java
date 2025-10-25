package com.software.software.repository;

import java.time.LocalDate;
import java.util.List;
import com.software.software.models.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query("SELECT a FROM Activity a WHERE a.dueDate = :tomorrow")
    List<Activity> findActivitiesDueTomorrow(LocalDate tomorrow);

}