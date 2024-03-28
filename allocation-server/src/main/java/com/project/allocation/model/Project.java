package com.project.allocation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

/**
 * Represents a project entity in the system. A project is proposed by a staff member and can
 * be associated with multiple students who express interest. Each project has a unique title,
 * a description, and a creation date to track when it was proposed.
 */
@Entity(name = "projects")
public class Project extends BaseEntity {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    /**
     * The staff member who proposed the project. This is a many-to-one relationship since
     * a staff member can propose multiple projects, but each project is proposed by a single staff.
     */
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_user_id", referencedColumnName = "id")
    private User staff; // The staff member who proposed the project

    private boolean status = false;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    public Project(String title, String description, User staff, boolean status) {
        this.title = title;
        this.description = description;
        this.staff = staff;
        this.status = status;
    }

    public Project() {

    }

    /**
     * Sets the creation date to the current date and time before persisting the project.
     */
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
