package com.project.allocation.service;

import com.project.allocation.dto.AuthResponseDTO;

/**
 * Service for authentication operations such as user login.
 */
public interface AuthService {
    /**
     * Attempts to log in a user with the given username and password.
     *
     * @param username The username of the user trying to log in.
     * @param password The password of the user trying to log in.
     * @return An {@link AuthResponseDTO} object containing authentication details, including a JWT token if login is successful.
     */
    AuthResponseDTO login(String username, String password);
}
