package com.project.allocation.service.impl;

import com.project.allocation.model.User;
import com.project.allocation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.allocation.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean createUser(User user) {
        boolean userExists = getUserByUsername(user.getUsername()) != null;
        if (!userExists) {
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public User updateUserPartially(Long id, User userUpdates) {
        return userRepository.findById(id).map(user -> {
            if (userUpdates.getEmail() != null) {
                user.setEmail(userUpdates.getEmail());
            }
            if (userUpdates.getPassword() != null) {
                user.setPassword(userUpdates.getPassword());
            }
            return userRepository.save(user);
        }).orElse(null);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean updateUserPassword(Long id, String oldPassword, String newPassword) {
        return false;
    }
}