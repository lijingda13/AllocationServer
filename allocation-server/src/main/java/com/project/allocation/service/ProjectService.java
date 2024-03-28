package com.project.allocation.service;

import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.model.Project;

import java.util.List;

/**
 * Service interface for managing projects. This includes operations for listing, creating, updating, and deleting projects, as well as managing user interest and assignments related to projects.
 */
public interface ProjectService {

    /**
     * Retrieves a list of all projects.
     *
     * @return A list of {@link Project} instances.
     */
    List<Project> listAllProjects();

    /**
     * Retrieves a list of available projects for a given student.
     *
     * @param userId The ID of the student.
     * @return A list of {@link StudentProjectDTO} instances representing projects the student can register interest in.
     */
    List<StudentProjectDTO> listAvailableProjects(Long userId);

    /**
     * Retrieves a list of projects proposed by a given staff member.
     *
     * @param userId The ID of the staff member.
     * @return A list of {@link StaffProjectDTO} instances representing projects proposed by the staff member.
     */
    List<StaffProjectDTO> listProposedProjects(Long userId);

    /**
     * Creates a new project proposed by a staff member.
     *
     * @param project The {@link Project} to be created.
     * @param staffId The ID of the staff member proposing the project.
     * @return The created {@link Project} instance.
     */
    Project createProject(Project project, Long staffId);

    /**
     * Updates an existing project with new information.
     *
     * @param project The {@link Project} instance containing the updated information.
     * @return The updated {@link Project} instance.
     */
    Project updateProject(Project project);

    /**
     * Deletes a project by its ID.
     *
     * @param projectId The ID of the project to be deleted.
     * @return {@code true} if the project was successfully deleted, {@code false} otherwise.
     */
    boolean deleteProject(Long projectId);

    /**
     * Registers a student's interest in a given project.
     *
     * @param projectId The ID of the project.
     * @param userId    The ID of the student registering interest.
     * @return {@code true} if interest was successfully registered, {@code false} otherwise.
     */
    boolean registerInterest(Long projectId, Long userId);

    /**
     * Unregisters a student's interest in a given project.
     *
     * @param projectId The ID of the project.
     * @param userId    The ID of the student unregistering interest.
     * @return {@code true} if interest was successfully unregistered, {@code false} otherwise.
     */
    boolean unregisterInterest(Long projectId, Long userId);

    /**
     * Assigns a project to a student, marking it as taken.
     *
     * @param projectId The ID of the project to assign.
     * @param userId    The ID of the student to whom the project is being assigned.
     * @return {@code true} if the project was successfully assigned, {@code false} otherwise.
     */
    boolean assignProject(Long projectId, Long userId);
}
