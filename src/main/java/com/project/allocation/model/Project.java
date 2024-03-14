package com.project.allocation.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private static Long projectId;

    private String title;
    private String description;

    private User proposer; // The staff member who proposed the project
    private User assignedStudent; // The student assigned to the project
    private Set<User> interestStudents;
    private boolean status;
    private Date createTime;
    private Date assignTime;

    public Project() {
    }

    public boolean getStatus() {
        return status;
    }

    public Set<User> getInterestStudents() {
        return interestStudents;
    }
}
