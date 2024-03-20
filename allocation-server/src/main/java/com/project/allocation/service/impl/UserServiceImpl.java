package com.project.allocation.service.impl;

import com.project.allocation.dto.StudentInfoDTO;
import com.project.allocation.model.AssignRecord;
import com.project.allocation.model.InterestRecord;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import com.project.allocation.repository.AssignRecordRepository;
import com.project.allocation.repository.InterestRecordRepository;
import com.project.allocation.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.project.allocation.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AssignRecordRepository assignRecordRepository;
    private final InterestRecordRepository interestRecordRepository;

    public UserServiceImpl(UserRepository userRepository, AssignRecordRepository assignRecordRepository, InterestRecordRepository interestRecordRepository) {
        this.userRepository = userRepository;
        this.assignRecordRepository = assignRecordRepository;
        this.interestRecordRepository = interestRecordRepository;
    }

    @Override
    public void createUser(User user) {
        userRepository.save(user);
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

        List<InterestRecord> records = interestRecordRepository.findByStudentId(userId);
        List<Project> interestProjects = records.stream().map(InterestRecord::getProject).collect(Collectors.toList());
        studentInfoDTO.setInterestProjects(interestProjects);
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

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}