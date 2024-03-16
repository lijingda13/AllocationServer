package com.project.allocation.service;

import com.project.allocation.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.project.allocation.model.Project;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class ProjectServiceImplTest {

    @Autowired
    private ProjectServiceImpl projectService;

    @Test
    void testGetProjects() {
        List<Project> projects = projectService.listAllProjects();
        assertNotEquals(0, projects.size());

    }
}
