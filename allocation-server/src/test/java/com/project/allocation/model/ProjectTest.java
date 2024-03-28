package com.project.allocation.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests the functionality of the {@link Project} class, ensuring that all properties
 * are correctly set through the constructor and accessible via getters.
 */
public class ProjectTest {

    /**
     * Tests the construction of a {@link Project} instance and validates the correct
     * assignment and retrieval of its properties.
     */
    @Test
    void testProjectConstructAndMethod() {
        String username = "testUser";
        String password = "testPass";
        String firstname = "Test";
        String lastname = "User";
        String email = "test@gmail.com";
        User staff = new User(username, password, firstname, lastname, email);

        String title = "Test Project";
        String description = "This is a test project";
        Project project = new Project(title, description, staff, false);

        assertNotNull(project, "Project should not be null");
        assertEquals(title, project.getTitle(), "Title should match the one provided to the constructor");
        assertEquals(description, project.getDescription(), "Description should match the one provided to the constructor");
        assertEquals(staff, project.getStaff(), "Staff should match the one provided to the constructor");
        assertFalse(project.getStatus(), "Status should match the one provided to the constructor");
    }
}
