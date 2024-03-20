package com.project.allocation.controller;


import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import com.project.allocation.repository.ProjectRepository;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.impl.ProjectServiceImpl;
import com.project.allocation.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;


import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProjectControllerTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private ProjectController projectController;

    private Project project1;
    private Project project2;
    private User staff;
    private User student;

    @BeforeEach
    public void setUp() {
        staff = new User();
        staff.setRole(User.Role.STAFF);

        student = new User();
        student.setRole(User.Role.STUDENT);

        project1 = new Project();
        project1.setTitle("Test Project");
        project1.setDescription("Test Description");
        project1.setStaff(staff);
        project1.setStatus(false);

        project2 = new Project();
        project2.setTitle("Test Project 2");
        project2.setDescription("Test Description 2");
        project2.setStaff(staff);
        project2.setStatus(false);

    }


}
