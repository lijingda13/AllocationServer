package com.project.allocation.dto;

import com.project.allocation.model.Project;

import java.util.Date;

public class StudentProjectDTO extends Project {
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
