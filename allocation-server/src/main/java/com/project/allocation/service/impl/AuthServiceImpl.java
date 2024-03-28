package com.project.allocation.service.impl;

import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.model.User;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.AuthService;
import com.project.allocation.util.JwtUtil;
import org.springframework.stereotype.Service;

/**
 * Service implementation for authentication processes, handling user login and token generation.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * Constructs an AuthServiceImpl with necessary dependencies.
     *
     * @param userRepository Repository for user data access.
     * @param jwtUtil        Utility class for JWT operations.
     */
    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Attempts to authenticate a user with the given username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return An {@link AuthResponseDTO} containing the login result, message, and a JWT token if successful.
     */
    @Override
    public AuthResponseDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return AuthResponseDTO.fail("Invalid username or password");
        }
        return AuthResponseDTO.success(jwtUtil.createToken(user), user);
    }
}
