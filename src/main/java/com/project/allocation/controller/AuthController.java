package com.project.allocation.controller;

import com.project.allocation.model.User;
import com.project.allocation.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean loggedIn = authService.login(user.getUsername(), user.getPassword());
        return loggedIn
                ? ResponseEntity.ok("Logged in succesfully")
                : ResponseEntity.badRequest().body("Invalid username or password");

    }

    /**
     * Logout
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        boolean loggedOut = authService.logout();
        return loggedOut
                ? ResponseEntity.ok("Logged out succesfully")
                : ResponseEntity.internalServerError().body("Something went wrong");
    }
}
