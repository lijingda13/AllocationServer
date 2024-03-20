package com.project.allocation.dto;

import com.project.allocation.model.Project;
import com.project.allocation.model.User;

import java.util.List;

public class StaffProjectDTO extends Project {
    private List<User> interestStudents;

    private User assignedStudent;

    public List<User> getInterestStudents() {
        return interestStudents;
    }

    public void setInterestStudents(List<User> interestStudents) {
        this.interestStudents = interestStudents;
    }

    public User getAssignedStudent() {
        return assignedStudent;
    }

    public void setAssignedStudent(User assignedStudent) {
        this.assignedStudent = assignedStudent;
    }
}
