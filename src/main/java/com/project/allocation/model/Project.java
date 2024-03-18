package com.project.allocation.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Date;

@Entity(name = "projects")
public class Project extends BaseEntity {

    private String title;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "staff_user_id", referencedColumnName = "id")
    private User staff; // The staff member who proposed the project

    private boolean status;
    private Date createDate;

    public Project(String title, String description, User staff, boolean status, Date createDate) {
        this.title = title;
        this.description = description;
        this.staff = staff;
        this.status = status;
        this.createDate = createDate;
    }

    public Project() {

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

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
