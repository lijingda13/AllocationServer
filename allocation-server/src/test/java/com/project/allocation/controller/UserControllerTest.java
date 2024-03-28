package com.project.allocation.controller;

import com.project.allocation.dto.StudentInfoDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for {@link UserController}. This class contains integration tests for user-related
 * endpoints, ensuring that UserController correctly processes requests and interacts with the
 * UserService as expected.
 */
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

    /**
     * Sets up the mock MVC environment for testing.
     */

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Tests the user registration endpoint for a new student. Ensures successful
     * registration and the correct HTTP response.
     */
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

    /**
     * Tests the user registration endpoint for a new staff member. Ensures successful
     * registration and the correct HTTP response.
     */
    @Test
    public void testRegisterStaff() {
        User staff = new User();
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

    /**
     * Tests the retrieval of a user by ID when the user does not exist. Expects a
     * 404 Not Found response.
     */
    @Test
    public void testGetUserByIdWithNonExistedThenNotFound() {
        ResponseEntity<User> response = projectController.getUserById(100L);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Tests the retrieval of a user by ID when the user exists. Expects a 200 OK response
     * and the user's details in the response body.
     */
    @Test
    public void testGetUserByIdWithExistedThenFound() {
        ResponseEntity<User> response2 = projectController.getUserById(1L);
        assertNotNull(response2);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
    }

    /**
     * Tests updating user details. Ensures the user details are updated correctly
     * and returns a 200 OK response.
     */
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

    /**
     * Tests partially updating user details using the PATCH method. Ensures partial updates
     * are applied correctly and returns a 200 OK response.
     */
    @Test
    public void testPatchUser() {
        User user = new User();
        user.setFirstName("staff-test-first-name-updated-2");
        ResponseEntity<User> response = projectController.patchUser(1L, user);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Tests retrieving project information for a student. Ensures the correct information
     * is returned for a given student ID and returns a 200 OK response.
     */
    @Test
    public void testGetStudentProjectInfo() {
        ResponseEntity<StudentInfoDTO> response = projectController.getStudentProjectInfo(3L);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
