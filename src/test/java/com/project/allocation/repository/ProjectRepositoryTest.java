package com.project.allocation.repository;

import com.project.allocation.model.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;


    @Test
    public void testGetProjects() {
        List<Project> projects = projectRepository.findAll();
        assertNotEquals(0, projects.size());
    }


    @Test
    public void testGetProjectById() {
        Optional<Project> project = projectRepository.findById(1L);
        assertNotEquals(Optional.empty(), project);
    }
}
