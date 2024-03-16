package com.project.allocation.service;

import com.project.allocation.model.Project;
import com.project.allocation.model.User;

import java.util.List;

public interface ProjectService {

    /**
     * List all projects
     * @return List of projects
     */
    List<Project> listAllProjects();


    /**
     * Get a project by id
     * @param id Project id
     * @return Project
     */
    Project getProjectById(Long id);


    /**
     * Create a project
     * @param project Project to propose
     * @return Proposed project
     */
    Project createProject(Project project);

    /**
     * Update a project
     * @param project Project to update
     * @return Updated project
     */
    Project updateProject(Project project);

    /**
     * Delete a project
     * @param projectId Project id
     * @return boolean
     */
    boolean deleteProject(Long projectId);

    /**
     * Register interest in a project
     * @param projectId Project id
     * @param userId User id
     * @return boolean
     */
    boolean registerInterest(Long projectId, Long userId);

    /**
     * Assign a project to a user
     * @param projectId Project id
     * @param userId User id
     * @return boolean
     */
    boolean assignProject(Long projectId, Long userId);

}
