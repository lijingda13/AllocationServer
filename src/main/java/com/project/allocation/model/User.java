package com.project.allocation.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private Role role; // "STUDENT" or "STAFF"

    public User(String username, String password, String firstname, String lastname) {
        this.username=username;
        this.password=password;
        this.firstname=firstname;
        this.lastname=lastname;
    }

    public User() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }
 
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    // Role enum for better type safety
    public enum Role {
        STUDENT, STAFF
    }

}