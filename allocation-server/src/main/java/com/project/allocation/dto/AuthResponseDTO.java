package com.project.allocation.dto;

import com.project.allocation.model.User;

/**
 * Data Transfer Object for authentication responses.
 * This class encapsulates the authentication result, message, JWT token, and user details.
 */
public class AuthResponseDTO {

    private boolean result;

    private String message;

    private String token;

    private Long userId;

    private User user;

    /**
     * Default constructor for creating an empty AuthResponseDTO object.
     */
    public AuthResponseDTO() {
    }

    /**
     * Constructs an AuthResponseDTO with specified details.
     *
     * @param result  The result of the authentication attempt.
     * @param message A message describing the outcome of the authentication attempt.
     * @param token   The JWT token issued upon successful authentication.
     * @param user    The authenticated user's details.
     */
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

    /**
     * Creates a successful AuthResponseDTO object.
     *
     * @param token The JWT token issued upon successful authentication.
     * @param user  The authenticated user's details.
     * @return A new instance of AuthResponseDTO indicating a successful authentication.
     */
    public static AuthResponseDTO success(String token, User user) {
        return new AuthResponseDTO(true, "Login successful", token, user);
    }

    /**
     * Creates a failed AuthResponseDTO object.
     *
     * @param message A message describing the reason for authentication failure.
     * @return A new instance of AuthResponseDTO indicating a failed authentication.
     */
    public static AuthResponseDTO fail(String message) {
        return new AuthResponseDTO(false, message, null, null);
    }
}
