package com.software.software.services;

import com.software.software.models.Activity;
import com.software.software.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository atividadeRepository;

    public List<Activity> getOverdueActivities(LocalDate currentDate) {
        return atividadeRepository.findActivitiesWithDueDateLessThanOneDay(currentDate);
    }
}
