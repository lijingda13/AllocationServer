package com.project.allocation.service;

import com.project.allocation.model.User;
import com.project.allocation.repository.AssignRecordRepository;
import com.project.allocation.repository.InterestRecordRepository;
import com.project.allocation.repository.ProjectRepository;
import com.project.allocation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test suite for {@link UserService} focusing on the business logic associated
 * with user management, including CRUD operations and retrieval of user-related information.
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AssignRecordRepository assignRecordRepository;

    @Autowired
    private InterestRecordRepository interestRecordRepository;

    @Autowired
    private UserService userService;

    private User student;

    private User staff;

    @BeforeEach
    void setUp() {
        User student = new User();
        student.setUsername("test-student");
        student.setPassword("test-student-password");
        student.setFirstName("test-student-first-name");
        student.setLastName("test-student-last-name");
        student.setRole(User.Role.STUDENT);
        this.student = student;

        User staff = new User();
        staff.setUsername("test-staff");
        staff.setPassword("test-staff-password");
        staff.setFirstName("test-staff-first-name");
        staff.setLastName("test-staff-last-name");
        staff.setRole(User.Role.STAFF);
        this.staff = staff;
    }

    /**
     * Tests retrieval of a user by their ID.
     */
    @Test
    public void testGetUserById() {
        User user = userService.getUserById(1L);
        assertNotEquals(null, user);
        assertEquals("rwilliams", user.getUsername());
        assertEquals(User.Role.STAFF, user.getRole());
    }

    /**
     * Tests retrieval of a user by their username.
     */
    @Test
    public void testGetUserByUsername() {
        User user = userRepository.findByUsername("rwilliams");
        assertNotEquals(null, user);
        assertEquals("rwilliams", user.getUsername());
        assertEquals(User.Role.STAFF, user.getRole());
    }

    /**
     * Tests creating a new student user and validating their saved information.
     */
    @Test
    public void testCreateStudentUser() {
        userService.createUser(student);
        assertNotEquals(null, student);

        User student = userService.getUserByUsername("test-student");
        assertNotEquals(null, student);
        assertEquals("test-student-first-name", student.getFirstName());
        assertEquals("test-student-last-name", student.getLastName());
        assertEquals(User.Role.STUDENT, student.getRole());
    }

    /**
     * Tests creating a new staff user and validating their saved information.
     */
    @Test
    public void testCreateStaffUser() {
        userService.createUser(staff);
        assertNotEquals(null, staff);

        User staff = userService.getUserByUsername("test-staff");
        assertNotEquals(null, staff);
        assertEquals("test-staff-first-name", staff.getFirstName());
        assertEquals("test-staff-last-name", staff.getLastName());
        assertEquals(User.Role.STAFF, staff.getRole());
    }
}
