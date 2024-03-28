package com.project.allocation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

/**
 * Entity representing the assignment of a project to a student.
 * This class is used to persist data about which student is assigned to which project.
 * It includes references to the associated {@link User} entity representing the student and the {@link Project} entity.
 * The assignment date is automatically set to the current date when a new assignment record is persisted.
 */
@Entity(name = "assign_record")
public class AssignRecord extends BaseEntity {

    /**
     * The student assigned to the project.
     * This is a one-to-one relationship between the assignment record and the student.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_user_id", referencedColumnName = "id")
    private User student;

    /**
     * The project assigned to the student.
     * This is a one-to-one relationship between the assignment record and the project.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date assignDate;

    /**
     * Lifecycle method to set the assignment date to the current date before persisting the assignment record.
     */
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
