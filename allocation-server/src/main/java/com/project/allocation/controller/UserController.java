package com.project.allocation.controller;

import com.project.allocation.dto.StudentInfoDTO;
import com.project.allocation.service.UserService;
import com.project.allocation.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.allocation.model.User;

/**
 * Controller for user-related operations.
 */
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

    /**
     * Registers a new user with the provided user details.
     *
     * @param user The user to be registered.
     * @return A response entity with a message indicating success or failure.
     */
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        boolean userExists = userService.existsByUsername(user.getUsername());
        if (userExists) {
            return ResponseEntity.badRequest().body("Failed: Username has existed");
        }
        userService.createUser(user);
        return ResponseEntity.ok("Registration successfully");
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return A response entity containing the requested user or a not found status.
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Updates a user with the provided ID and user details.
     *
     * @param userId      The ID of the user to update.
     * @param userUpdates The user details to apply to the existing user.
     * @return A response entity containing the updated user or a not found status.
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userUpdates) {
        User updatedUser = userService.updateUser(userId, userUpdates);
        return updatedUser != null ? new ResponseEntity<>(updatedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Partially updates a user with the provided ID and user details.
     *
     * @param userId      The ID of the user to update.
     * @param userUpdates The user details to apply to the existing user.
     * @return A response entity containing the patched user or a not found status.
     */
    @PatchMapping("/users/{userId}")
    public ResponseEntity<User> patchUser(@PathVariable Long userId, @RequestBody User userUpdates) {
        User patchedUser = userService.updateUserPartially(userId, userUpdates);
        return patchedUser != null ? new ResponseEntity<>(patchedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Retrieves project information for a student user by their ID.
     *
     * @param userId The ID of the user to retrieve project information for.
     * @return A response entity containing the student's project information or an appropriate error status.
     */
    @GetMapping("/users/{userId}/student-info")
    public ResponseEntity<StudentInfoDTO> getStudentProjectInfo(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!user.getRole().equals(User.Role.STUDENT)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StudentInfoDTO studentInfo = userService.getStudentInfoById(userId);
        return studentInfo != null ? new ResponseEntity<>(studentInfo, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
