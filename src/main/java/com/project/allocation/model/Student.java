package com.project.allocation.model;

import java.util.Set;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

public class Student extends User {
    @ManyToMany(mappedBy = "interestedUsers")
    private Set<Project> interestProject;
    @ManyToOne
    private Project assignedProject;

    public Student() {
        super();
    }

    public Set<Project> getInterestProject() {
        return interestProject;
    }

    public void setInterestProject(Set<Project> interestProject) {
        this.interestProject = interestProject;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public void setAssignedProject(Project assignedProject) {
        this.assignedProject = assignedProject;
    }
}
