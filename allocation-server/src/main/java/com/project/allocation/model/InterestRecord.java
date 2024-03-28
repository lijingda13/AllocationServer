package com.project.allocation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Represents a record of interest from a student for a specific project.
 * This entity links students with projects they are interested in, allowing
 * for the tracking and management of project interest within the system.
 */
@Entity(name = "interest_record")
public class InterestRecord extends BaseEntity {

    /**
     * The student who has expressed interest in the project. This is a many-to-one relationship.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_user_id", referencedColumnName = "id")
    private User student;

    /**
     * The project that the student is interested in. This is a many-to-one relationship.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    public InterestRecord() {
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
}
