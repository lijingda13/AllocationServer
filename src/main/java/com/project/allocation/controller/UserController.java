package com.project.allocation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.allocation.model.Staff;
import com.project.allocation.model.Student;
import com.project.allocation.model.User;
import com.project.allocation.model.User.Role;
import com.project.allocation.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register/staff")
    public ResponseEntity<User> registerStaff(@RequestBody Staff staff) {
        User newUser = userService.registerUser(staff, Role.STAFF);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/register/student")
    public ResponseEntity<User> registerStudent(@RequestBody Student student) {
        User newUser = userService.registerUser(student, Role.STUDENT);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

}