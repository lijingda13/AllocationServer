package com.project.allocation.model;

import java.util.Set;

public class Project {
    private Long id;

    private String title;
    private String description;

    private User proposer; // The staff member who proposed the project
    private User assignedStudent; // The student assigned to the project
    private Set<User> interestStudents;
    private boolean status;

    public boolean getStatus() {
        return status;
    }

    public Set<User> getInterestStudents() {
        return interestStudents;
    }
}
