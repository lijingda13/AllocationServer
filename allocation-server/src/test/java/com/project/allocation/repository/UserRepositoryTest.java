package com.project.allocation.repository;

import com.project.allocation.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void testGetUser() {
        List<User> users = userRepository.findAll();
        assertNotEquals(0, users.size());
    }

    @Test
    public void testGetUserByUsername() {
        User user = userRepository.findByUsername("rwilliams");
        assertNotEquals(null, user);
    }

    @Test
    public void testGetUserById() {
        User user = userRepository.findById(1);
        assertNotEquals(null, user);
    }

    @Test
    public void testCreateStudent() {
        User user = new User();
        user.setUsername("test-student");
        user.setPassword("test-student-password");
        user.setFirstName("test-student-first-name");
        user.setLastName("test-student-last-name");
        user.setRole(User.Role.STUDENT);
        userRepository.save(user);
        assertNotEquals(null, user);

        User student = userRepository.findByUsername("test-student");
        assertNotEquals(null, student);
        assertEquals("test-student-first-name", student.getFirstName());
        assertEquals("test-student-last-name", student.getLastName());
        assertEquals(User.Role.STUDENT, student.getRole());
    }

    @Test
    public void testCreateStaff() {
        User user = new User();
        user.setUsername("test-staff");
        user.setPassword("test-staff-password");
        user.setFirstName("test-staff-first-name");
        user.setLastName("test-staff-last-name");
        user.setRole(User.Role.STAFF);
        userRepository.save(user);
        assertNotEquals(null, user);

        User staff = userRepository.findByUsername("test-staff");
        assertNotEquals(null, staff);
        assertEquals("test-staff-first-name", staff.getFirstName());
        assertEquals("test-staff-last-name", staff.getLastName());
        assertEquals(User.Role.STAFF, staff.getRole());
    }

    @Test
    public void testUpdateUser() {
        User user = userRepository.findByUsername("rwilliams");
        assertNotEquals("newFirstName", user.getFirstName());
        user.setFirstName("newFirstName");
        userRepository.save(user);
        User updatedUser = userRepository.findByUsername("rwilliams");
        assertEquals("newFirstName", updatedUser.getFirstName());
    }

    @Test
    public void testExistsByUsername() {
        boolean exists = userRepository.existsByUsername("rwilliams");
        assertTrue(exists);

        boolean notExists = userRepository.existsByUsername("notExists");
        assertFalse(notExists);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@email.com");
        userRepository.save(user);
        User savedUser = userRepository.findByUsername("testUser");
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("testPass", savedUser.getPassword());
    }

    @Test
    public void testUpdateUserPassword() {
        User user = userRepository.findByUsername("rwilliams");
        assertNotEquals("newPassword", user.getPassword());
        user.setPassword("newPassword");
        userRepository.save(user);
        User updatedUser = userRepository.findByUsername("rwilliams");
        assertEquals("newPassword", updatedUser.getPassword());
    }
}
