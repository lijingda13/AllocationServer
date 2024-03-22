package com.project.allocation.controller;


import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import com.project.allocation.repository.AssignRecordRepository;
import com.project.allocation.repository.InterestRecordRepository;
import com.project.allocation.repository.ProjectRepository;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.AuthService;
import com.project.allocation.service.ProjectService;
import com.project.allocation.service.UserService;
import com.project.allocation.service.impl.ProjectServiceImpl;
import com.project.allocation.service.impl.UserServiceImpl;
import com.project.allocation.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;


import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRecordRepository interestRecordRepository;

    @Autowired
    private AssignRecordRepository assignRecordRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ProjectController projectController;

    private Project project1;
    private Project project2;
    private User staff;
    private User student;
    private String studentToken;
    private String staffToken;


    @BeforeEach
    public void setUp() {
        AuthResponseDTO staff = authService.login("rwilliams", "123456");
        AuthResponseDTO student = authService.login("jchen", "123456");

        project1 = new Project();
        project1.setTitle("Project 1");
        project1.setDescription("Description 1");
        project1.setStaff(staff.getUser());
        project1.setStatus(true);

        project2 = new Project();
        project2.setTitle("Project 2");
        project2.setDescription("Description 2");
        project2.setStaff(staff.getUser());
        project2.setStatus(true);

    }

    @Test
    public void testlistAllProject() {
        ResponseEntity<List<Project>> response = projectController.listAllProjects();
        List<Project> projects = response.getBody();
        assertNotNull(projects);
        assertEquals(4, projects.size());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testlistAvailableProjects() {
        ResponseEntity<List<StudentProjectDTO>> response = projectController.listAvailableProjects(1L);
        List<StudentProjectDTO> projects = response.getBody();
        assertNotNull(projects);
        assertEquals(3, projects.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(projects.get(0).getStatus());
    }

    @Test
    public void testCreateProject() {
        ResponseEntity<Project> response = projectController.createProject(project1);
        Project project = response.getBody();
        assertNotNull(project);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Project 1", project.getTitle());
        assertEquals("Description 1", project.getDescription());
        assertEquals(5, projectRepository.findAll().size());
    }

    @Test
    public void testUpdateProject() {
        ResponseEntity<Project> response = projectController.createProject(project2);
        Project project = response.getBody();
        assertNotNull(project);
        project.setTitle("Project 3 Updated");
        ResponseEntity<Project> updatedResponse = projectController.updateProject(project.getId(), project);
        Project updatedProject = updatedResponse.getBody();
        assertNotNull(updatedProject);
        assertEquals(HttpStatus.OK, updatedResponse.getStatusCode());
        assertEquals("Project 3 Updated", updatedProject.getTitle());
    }

}
