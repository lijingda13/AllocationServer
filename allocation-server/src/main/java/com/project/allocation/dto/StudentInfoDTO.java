package com.project.allocation.dto;

import com.project.allocation.model.Project;
import com.project.allocation.model.User;

import java.util.List;

public class StudentInfoDTO extends User {
    private boolean assignedStatus;

    private Project assignedProject;

    private List<Project> interestProjects;

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

    public List<Project> getInterestProjects() {
        return interestProjects;
    }

    public void setInterestProjects(List<Project> interestProjects) {
        this.interestProjects = interestProjects;
    }
}
