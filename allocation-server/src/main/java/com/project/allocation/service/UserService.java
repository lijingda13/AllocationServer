package com.project.allocation.service;

import com.project.allocation.dto.StudentInfoDTO;
import com.project.allocation.model.User;

/**
 * Service interface for user management, handling CRUD operations and other user-related functionalities.
 */
public interface UserService {

    /**
     * Creates a new user in the system.
     *
     * @param user The {@link User} entity to be created.
     */
    void createUser(User user);

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} entity if found, {@code null} otherwise.
     */
    User getUserById(Long id);

    /**
     * Updates a user's information with new data provided in the updatedUser object.
     *
     * @param id          The ID of the user to update.
     * @param updatedUser An {@link User} object containing updated fields.
     * @return The updated {@link User} entity.
     */
    User updateUser(Long id, User updatedUser);

    /**
     * Updates specific fields of a user's information, ignoring null values in the updatedUser object.
     *
     * @param id          The ID of the user to update.
     * @param updatedUser An {@link User} object containing updated fields.
     * @return The partially updated {@link User} entity.
     */
    User updateUserPartially(Long id, User updatedUser);

    /**
     * Retrieves comprehensive information about a student, including their assigned and interested projects.
     *
     * @param userId The ID of the student.
     * @return A {@link StudentInfoDTO} containing the student's project information.
     */
    StudentInfoDTO getStudentInfoById(Long userId);

    /**
     * Deletes a user from the system by their ID.
     *
     * @param id The ID of the user to delete.
     * @return {@code true} if the user was successfully deleted, {@code false} otherwise.
     */
    boolean deleteUser(Long id);

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The {@link User} entity if found, {@code null} otherwise.
     */
    User getUserByUsername(String username);

    /**
     * Updates a user's password, ensuring the old password matches before making the change.
     *
     * @param id          The ID of the user whose password is to be updated.
     * @param oldPassword The current password of the user.
     * @param newPassword The new password to set.
     * @return {@code true} if the password was successfully updated, {@code false} otherwise.
     */
    boolean updateUserPassword(Long id, String oldPassword, String newPassword);

    /**
     * Checks if a user with the given username exists in the system.
     *
     * @param username The username to check.
     * @return {@code true} if a user with the given username exists, {@code false} otherwise.
     */
    boolean existsByUsername(String username);
}
