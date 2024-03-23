package com.project.allocation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity(name = "projects")
public class Project extends BaseEntity {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_user_id", referencedColumnName = "id")
    private User staff; // The staff member who proposed the project

    private boolean status = false;
    private Date createDate;

    public Project(String title, String description, User staff, boolean status) {
        this.title = title;
        this.description = description;
        this.staff = staff;
        this.status = status;
    }

    public Project() {

    }

    @PrePersist
    protected void onCreate() {
        this.createDate = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getStaff() {
        return staff;
    }

    public void setStaff(User staff) {
        this.staff = staff;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }
}
