package com.project.allocation.dto;

import com.project.allocation.model.Project;

public class StudentProjectDTO extends Project {
    private boolean registerStatus;

    public boolean isRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(boolean registerStatus) {
        this.registerStatus = registerStatus;
    }
}
