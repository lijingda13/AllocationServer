package com.project.allocation.controller;

import com.project.allocation.service.UserService;
import com.project.allocation.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.allocation.model.User;
import com.project.allocation.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        boolean created = userService.createUser(user);
        if (created) {
            return ResponseEntity.ok("Registration successful");
        } else {
            return ResponseEntity.badRequest().body("Failed: Username has existed");
        }
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserInformation(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
//        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userUpdates) {
        User updatedUser = userService.updateUser(userId, userUpdates);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> patchUser(@PathVariable Long userId, @RequestBody User userUpdates) {
        User patchedUser = userService.updateUserPartially(userId, userUpdates);

        if (patchedUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patchedUser);
    }
}
