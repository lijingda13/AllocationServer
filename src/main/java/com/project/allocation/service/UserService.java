package com.project.allocation.service;

import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.model.User;

import java.util.List;

public interface UserService {

    boolean createUser(User user);

    User updateUser(Long id, User updatedUser);

    User updateUserPartially(Long id, User updatedUser);

    User getUserById(Long id);

    boolean deleteUser(Long id);

    User getUserByUsername(String username);

    boolean updateUserPassword(Long id, String oldPassword, String newPassword);
}
