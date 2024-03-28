package com.project.allocation.controller;

import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.model.User;
import com.project.allocation.service.AuthService;
import com.project.allocation.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for authentication-related operations.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Authenticates a user based on username and password.
     *
     * @param user A user object containing the username and password.
     * @return A response entity with the authentication result, including a JWT token if successful.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody User user) {
        AuthResponseDTO dto = authService.login(user.getUsername(), user.getPassword());
        if (dto.getResult()) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.badRequest().body(dto);
        }
    }
}
