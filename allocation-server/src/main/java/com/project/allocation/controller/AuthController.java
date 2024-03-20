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
     * Login
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

    @GetMapping("/hello")
    public ResponseEntity<String> hello(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String userId = jwtUtil.getUserIdFromToken(token);
        return ResponseEntity.ok("Hello " + userId);
    }

    @GetMapping("/hello_staff")
    public ResponseEntity<String> helloStaff(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String userId = jwtUtil.getUserIdFromToken(token);
        return ResponseEntity.ok("Hello staff " + userId);
    }

    @GetMapping("/hello_student")
    public ResponseEntity<String> helloStudent(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String userId = jwtUtil.getUserIdFromToken(token);
        return ResponseEntity.ok("Hello student " + userId);
    }
}
