package com.project.allocation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.allocation.model.Project;

import java.util.Date;

public class StudentProjectDTO extends Project {
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
