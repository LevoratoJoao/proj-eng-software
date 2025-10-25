package com.software.software.observers;

import com.software.software.models.Activity;

public interface ActivityObserver {
    void onActivityOverdue(Long studentId, Activity activity);
}
