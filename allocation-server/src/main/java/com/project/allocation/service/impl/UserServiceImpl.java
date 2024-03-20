package com.project.allocation.service.impl;

import com.project.allocation.dto.StudentInfoDTO;
import com.project.allocation.model.AssignRecord;
import com.project.allocation.model.User;
import com.project.allocation.repository.AssignRecordRepository;
import com.project.allocation.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.project.allocation.service.UserService;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AssignRecordRepository assignRecordRepository;


    public UserServiceImpl(UserRepository userRepository, AssignRecordRepository assignRecordRepository) {
        this.userRepository = userRepository;
        this.assignRecordRepository = assignRecordRepository;
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
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
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
    public StudentInfoDTO getStudentInfoById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }
        StudentInfoDTO studentInfoDTO = new StudentInfoDTO();
        BeanUtils.copyProperties(user, studentInfoDTO);
        boolean assigned = assignRecordRepository.existsByStudentId(userId);
        studentInfoDTO.setAssignedStatus(assigned);
        Optional<AssignRecord> assignRecord = assignRecordRepository.findByStudentId(userId);
        assignRecord.ifPresent(record -> studentInfoDTO.setAssignedProject(record.getProject()));
        return studentInfoDTO;
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