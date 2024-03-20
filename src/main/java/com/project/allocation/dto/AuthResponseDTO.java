package com.project.allocation.dto;

import com.project.allocation.model.User;

public class AuthResponseDTO {

    private boolean result;

    private String message;

    private String token;

    private Long userId;

    private User user;

    public AuthResponseDTO(boolean result, String message, String token, User user) {
        this.result = result;
        this.message = message;
        this.token = token;
        this.user = user;
        if (user != null && user.getId() != null) {
            setUserId(user.getId());
        }
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static AuthResponseDTO success(String token, User user) {
        return new AuthResponseDTO(true, "Login successful", token, user);
    }

    public static AuthResponseDTO fail(String message) {
        return new AuthResponseDTO(false, message, null, null);
    }
}
