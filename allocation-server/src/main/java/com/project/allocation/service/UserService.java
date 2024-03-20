package com.project.allocation.service;

import com.project.allocation.dto.StudentInfoDTO;
import com.project.allocation.model.User;

public interface UserService {

    boolean createUser(User user);

    User getUserById(Long id);

    User updateUser(Long id, User updatedUser);

    User updateUserPartially(Long id, User updatedUser);

    StudentInfoDTO getStudentInfoById(Long userId);

    boolean deleteUser(Long id);

    User getUserByUsername(String username);

    boolean updateUserPassword(Long id, String oldPassword, String newPassword);
}
