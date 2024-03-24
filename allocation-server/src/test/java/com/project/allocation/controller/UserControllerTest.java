package com.project.allocation.controller;


import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.dto.StudentInfoDTO;
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
import com.project.allocation.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
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
    private UserController projectController;
    @Autowired
    private UserController userController;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testRegisterStudent() {

        User student = new User();
        student.setUsername("student-test");
        student.setPassword("student-test-password");
        student.setFirstName("student-test-first-name");
        student.setLastName("student-test-last-name");
        student.setRole(User.Role.STUDENT);

        ResponseEntity<String> response = projectController.registerUser(student);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registration successfully", response.getBody());
    }

    @Test
    public void testRegisterStaff() {
        User  staff = new User();
        staff.setUsername("staff-test");
        staff.setPassword("staff-test-password");
        staff.setFirstName("staff-test-first-name");
        staff.setLastName("staff-test-last-name");
        staff.setRole(User.Role.STAFF);
        ResponseEntity<String> response = projectController.registerUser(staff);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registration successfully", response.getBody());
    }

    @Test
    public void testGetUserByIdWithNonExistedThenNotFound() {
        ResponseEntity<User> response = projectController.getUserById(100L);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetUserByIdWithExistedThenFound() {
        ResponseEntity<User> response2 = projectController.getUserById(1L);
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setUsername("staff-test-2");
        user.setPassword("staff-test-password");
        user.setFirstName("staff-test-first-name");
        user.setLastName("staff-test-last-name");
        user.setRole(User.Role.STAFF);

        ResponseEntity<String> response = projectController.registerUser(user);
        user.setFirstName("staff-test-first-name-updated");
        ResponseEntity<User> response2 = projectController.updateUser(7L, user);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testPatchUser() {
        User user = new User();
        user.setFirstName("staff-test-first-name-updated-2");
        ResponseEntity<User> response = projectController.patchUser(1L, user);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetStudentProjectInfo() {
        ResponseEntity<StudentInfoDTO> response = projectController.getStudentProjectInfo(3L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
