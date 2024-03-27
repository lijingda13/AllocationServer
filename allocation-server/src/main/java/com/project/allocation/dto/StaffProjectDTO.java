package com.project.allocation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;

import java.util.Date;
import java.util.List;

public class StaffProjectDTO extends Project {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private List<User> interestStudents;

    private User assignedStudent;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

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
