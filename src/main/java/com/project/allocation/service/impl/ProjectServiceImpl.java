package com.project.allocation.service.impl;

import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.model.Project;
import com.project.allocation.repository.ProjectRepository;
import com.project.allocation.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> listAllProjects() {
        return List.of();
    }

    @Override
    public Project getProjectById(Long id) {
        return null;
    }

    @Override
    public Project createProject(Project project) {
        return null;
    }

    @Override
    public Project updateProject(Project project) {
        return null;
    }

    @Override
    public boolean deleteProject(Long projectId) {
        return false;
    }

    @Override
    public boolean registerInterest(Long projectId, Long userId) {
        return false;
    }

    @Override
    public boolean assignProject(Long projectId, Long userId) {
        return false;
    }

    @Override
    public List<StudentProjectDTO> listAvailableProjects(Long studentId) {

        return null;
    }

    @Override
    public List<StaffProjectDTO> listProposedProjects(Long staffId) {
        return null;
    }


}
