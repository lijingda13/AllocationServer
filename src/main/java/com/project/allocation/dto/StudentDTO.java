package com.project.allocation.dto;

import com.project.allocation.model.User;

public class StudentDTO extends User {
    private boolean assignedStatus;

    public boolean isAssignedStatus() {
        return assignedStatus;
    }

    public void setAssignedStatus(boolean assignedStatus) {
        this.assignedStatus = assignedStatus;
    }
}
