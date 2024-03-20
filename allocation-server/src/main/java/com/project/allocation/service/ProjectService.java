package com.project.allocation.service;

import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.model.Project;

import java.util.List;

public interface ProjectService {

    /**
     * List all projects
     *
     * @return List of projects
     */
    List<Project> listAllProjects();

    List<StudentProjectDTO> listAvailableProjects(Long userId);

    List<StaffProjectDTO> listProposedProjects(Long userId);

    /**
     * Create a project
     *
     * @param project Project to propose
     * @return Proposed project
     */
    Project createProject(Project project);

    /**
     * Update a project
     *
     * @param project Project to update
     * @return Updated project
     */
    Project updateProject(Project project);

    /**
     * Delete a project
     *
     * @param projectId Project id
     * @return boolean
     */
    boolean deleteProject(Long projectId);

    /**
     * Register interest in a project
     *
     * @param projectId Project id
     * @param userId    User id
     * @return boolean
     */
    boolean registerInterest(Long projectId, Long userId);

    /**
     * Assign a project to a user
     *
     * @param projectId Project id
     * @param userId    User id
     * @return boolean
     */
    boolean assignProject(Long projectId, Long userId);

    Project getAssignedProject(Long userId);

}
