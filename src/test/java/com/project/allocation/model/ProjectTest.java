package com.project.allocation.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectTest {

    @Test
    void createProjectWithConstructorAndValidateFields() {
        String username = "testUser";
        String password = "testPass";
        String firstname = "Test";
        String lastname = "User";
        String email = "test@gmail.com";
        User staff = new User(username, password, firstname, lastname, email);

        String title = "Test Project";
        String description = "This is a test project";
        Project project = new Project(title, description, staff, false, null);


        assertNotNull(project, "Project should not be null");
        assertEquals(title, project.getTitle(), "Title should match the one provided to the constructor");
        assertEquals(description, project.getDescription(), "Description should match the one provided to the constructor");
        assertEquals(staff, project.getStaff(), "Staff should match the one provided to the constructor");
        assertFalse(project.getStatus(), "Status should match the one provided to the constructor");
    }
}
