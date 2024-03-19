package com.project.allocation.controller;

import com.project.allocation.dto.StudentDTO;
import com.project.allocation.repository.AssignRecordRepository;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.UserService;
import com.project.allocation.util.JwtUtil;
import org.springframework.beans.BeanUtils;
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
    private final UserRepository userRepository;
    private final AssignRecordRepository assignRecordRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, AssignRecordRepository assignRecordRepository, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.assignRecordRepository = assignRecordRepository;
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
    public ResponseEntity<StudentDTO> getUserInformation(@PathVariable Long userId) {
        StudentDTO studentDTO = userService.getStudentInfoById(userId);
        return studentDTO != null ? new ResponseEntity<>(studentDTO, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userUpdates) {
        User updatedUser = userService.updateUser(userId, userUpdates);
        return updatedUser != null ? new ResponseEntity<>(updatedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/users/{userId}")
    public ResponseEntity<User> patchUser(@PathVariable Long userId, @RequestBody User userUpdates) {
        User patchedUser = userService.updateUserPartially(userId, userUpdates);
        return patchedUser != null ? new ResponseEntity<>(patchedUser, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
