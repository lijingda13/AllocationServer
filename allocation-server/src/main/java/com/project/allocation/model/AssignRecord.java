package com.project.allocation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "assign_record")
public class AssignRecord extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_user_id", referencedColumnName = "id")
    private User student;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date assignDate;

    @PrePersist
    protected void onAssign() {
        this.assignDate = new Date();
    }

    public AssignRecord() {
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }
}
