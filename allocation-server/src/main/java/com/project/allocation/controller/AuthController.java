package com.project.allocation.controller;

import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.model.User;
import com.project.allocation.service.AuthService;
import com.project.allocation.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;

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

    /**
     * A simple endpoint for testing successful authentication.
     *
     * @param token The JWT token provided in the request header.
     * @return A greeting message with the user ID extracted from the JWT token.
     */
    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        return ResponseEntity.ok("Hello " + userId);
    }

    /**
     * A staff-specific endpoint for testing successful authentication and authorization.
     *
     * @param token The JWT token provided in the request header.
     * @return A staff-specific greeting message with the user ID extracted from the JWT token.
     */
    @GetMapping("/hello_staff")
    public ResponseEntity<String> helloStaff(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        return ResponseEntity.ok("Hello staff " + userId);
    }

    /**
     * A student-specific endpoint for testing successful authentication and authorization.
     *
     * @param token The JWT token provided in the request header.
     * @return A student-specific greeting message with the user ID extracted from the JWT token.
     */
    @GetMapping("/hello_student")
    public ResponseEntity<String> helloStudent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        return ResponseEntity.ok("Hello student " + userId);
    }
}
