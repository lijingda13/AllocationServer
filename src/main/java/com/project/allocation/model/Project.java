package com.project.allocation.model;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private static Long projectId;

    private String title;
    private String description;

    private User proposer; // The staff member who proposed the project

    @ManyToOne
    @JoinColumn(name = "assigned_user_id")
    private User assignedStudent; // The student assigned to the project

    @ManyToMany
    @JoinTable(
        name = "project_interests",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
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
