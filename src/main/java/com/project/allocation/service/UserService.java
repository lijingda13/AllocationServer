package com.project.allocation.service;

import com.project.allocation.model.User;

public interface UserService {

    User createUser(User user);

    User updateUser(User user);

    User getUserById(Long id);

    boolean deleteUser(Long id);

    User getUserByUsername(String username);

    boolean updateUserPassword(Long id, String oldPassword, String newPassword);
}
