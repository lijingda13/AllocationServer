package com.project.allocation.model;

import java.util.Set;

import jakarta.persistence.ManyToMany;

public class Student extends User{
    @ManyToMany(mappedBy = "interestedUsers")
    private Set<Project> interestProject;
}
