package com.project.allocation.dto;

import com.project.allocation.model.Project;
import com.project.allocation.model.User;

import java.util.List;

/**
 * Data Transfer Object for conveying information about a student, including their assignment status,
 * the project they are assigned to (if any), and a list of projects they have expressed interest in.
 * This class extends {@link User}, inheriting user-related properties such as username, first name, last name, etc.
 */
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
