package com.project.allocation.dto;

import com.project.allocation.model.Project;
import com.project.allocation.model.User;

public class StudentInfoDTO extends User {
    private boolean assignedStatus;

    private Project assignedProject;

    public boolean isAssignedStatus() {
        return assignedStatus;
    }

    public void setAssignedStatus(boolean assignedStatus) {
        this.assignedStatus = assignedStatus;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
    }
}
