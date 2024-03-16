package com.project.allocation.service.impl;

import com.project.allocation.model.User;
import org.springframework.stereotype.Service;
import com.project.allocation.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean updateUserPassword(Long id, String oldPassword, String newPassword) {
        return false;
    }
}