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
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        staff.setRole(role); 
        return userRepository.save(staff);
    }

    public User registerUser(Student student, Role role) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(role); 
        return userRepository.save(student);
    }
    //login
    
}
