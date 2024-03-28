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

/**
 * Service implementation for user management.
 * Handles operations such as creating, retrieving, updating, and deleting users,
 * as well as specific functionalities like fetching student project information.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AssignRecordRepository assignRecordRepository;
    private final InterestRecordRepository interestRecordRepository;

    /**
     * Constructs a new UserServiceImpl with necessary repositories.
     *
     * @param userRepository           the repository for user operations
     * @param assignRecordRepository   the repository for assignment records
     * @param interestRecordRepository the repository for interest records
     */
    public UserServiceImpl(UserRepository userRepository, AssignRecordRepository assignRecordRepository, InterestRecordRepository interestRecordRepository) {
        this.userRepository = userRepository;
        this.assignRecordRepository = assignRecordRepository;
        this.interestRecordRepository = interestRecordRepository;
    }

    /**
     * Creates and saves a new user.
     *
     * @param user the user to be saved
     */
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return the found User, or null if not found
     */
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Updates a user with new information.
     *
     * @param id          the ID of the user to update
     * @param updatedUser the new user information to update
     * @return the updated User, or null if the user was not found
     */
    @Override
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        }).orElse(null);
    }

    /**
     * Partially updates a user's information.
     *
     * @param id          the ID of the user to update
     * @param userUpdates the new user information to apply
     * @return the updated User, or null if the user was not found
     */
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

    /**
     * Fetches detailed project information for a student.
     *
     * @param userId the ID of the student
     * @return a StudentInfoDTO containing the student's project information, or null if the user was not found
     */
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

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @return true if the user was successfully deleted, false otherwise
     */
    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve
     * @return the found User, or null if not found
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Updates a user's password.
     *
     * @param id          the ID of the user whose password is to be updated
     * @param oldPassword the user's current password
     * @param newPassword the new password for the user
     * @return true if the password was successfully updated, false otherwise
     */
    @Override
    public boolean updateUserPassword(Long id, String oldPassword, String newPassword) {
        return false;
    }

    /**
     * Checks if a user exists by their username.
     *
     * @param username the username to check
     * @return true if a user with the given username exists, false otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}