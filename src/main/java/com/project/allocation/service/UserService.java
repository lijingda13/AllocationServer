package com.project.allocation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.allocation.model.Staff;
import com.project.allocation.model.Student;
import com.project.allocation.model.User;
import com.project.allocation.model.User.Role;
import com.project.allocation.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(Staff staff) {
        staff.setPassword(passwordEncoder.encode(staff.getPassword()));
        staff.setRole(staff.getRole()); 
        return userRepository.save(staff);
    }

    public User registerUser(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole(student.getRole()); 
        return userRepository.save(student);
    }

    public User updateUser(User userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userDetails.getId()));
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user); // Saving the updated user back to the database
    }

    //login
    public boolean login(String role, String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null || !user.getRole().toString().equalsIgnoreCase(role)) {
            return false; // User not found or role mismatch
        }
        // Verify password
        return passwordEncoder.matches(password, user.getPassword());
    }
    
}
