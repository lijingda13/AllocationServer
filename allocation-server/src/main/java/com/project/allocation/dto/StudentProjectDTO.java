package com.project.allocation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.allocation.model.Project;

import java.util.Date;

/**
 * Data Transfer Object that extends {@link Project} to include additional information relevant to students,
 * such as the project's creation date and the student's registration status for this project.
 */
public class StudentProjectDTO extends Project {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private boolean registerStatus;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(boolean registerStatus) {
        this.registerStatus = registerStatus;
    }
}
