package com.project.allocation.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a user in the system, which can be either a student or a staff member.
 * Each user has a unique username and password, along with personal information such as
 * first name, last name, and email address. Users are differentiated by their roles in
 * the system to provide appropriate access and functionalities.
 */
@Entity(name = "users")
public class User extends BaseEntity {

    /**
     * Defines the possible roles for users within the system for type safety and clarity.
     */
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
    private String email;
    protected Role role; // "STUDENT" or "STAFF"

    public User(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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