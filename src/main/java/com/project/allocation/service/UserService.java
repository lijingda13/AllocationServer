package com.project.allocation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.allocation.model.Staff;
import com.project.allocation.model.Student;
import com.project.allocation.model.User;
import com.project.allocation.model.User.Role;
import com.project.allocation.repository.UserRepository;

public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(Staff staff, Role role) {
        // Here we would set the role, hash the password, and then save the staff to the database.
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        staff.setRole(role); // Assuming you have a setRole method in your User class.
        return userRepository.save(staff);
    }

    public User registerUser(Student student, Role role) {
        // Similar to registering staff, we set the role, hash the password, and save the student.
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(role); // Assuming you have a setRole method in your User class.
        return userRepository.save(student);
    }
    
}
