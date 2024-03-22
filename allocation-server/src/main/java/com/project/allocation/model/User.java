package com.project.allocation.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

@Entity(name = "users")
public class User extends BaseEntity {
    // Role enum for better type safety
    public enum Role {
        STUDENT, STAFF
    }

    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "First name cannot be blank")
    private String firstname;
    @NotBlank(message = "Last name cannot be blank")
    private String lastname;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Role cannot be blank")
    protected Role role; // "STUDENT" or "STAFF"

    public User(String username, String password, String firstname, String lastname, String email, Role role) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
    }

    public User() {
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstName() {
        return firstname;
    }


    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }


}