package com.software.software.patterns;

import com.software.software.models.Activity;

public interface ActivityObserver {
    void onActivityOverdue(Long studentId, Activity activity);
}
