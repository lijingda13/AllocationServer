package com.project.allocation.dto;

public class AuthResponseDTO {

    private boolean result;

    private String message;

    private String token;

    public AuthResponseDTO(boolean result, String message, String token) {
        this.result = result;
        this.message = message;
        this.token = token;
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

    public static AuthResponseDTO success(String token) {
        return new AuthResponseDTO(true, "Login successful", token);
    }

    public static AuthResponseDTO fail(String message) {
        return new AuthResponseDTO(false, message, null);
    }
}
