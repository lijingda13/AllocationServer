package com.project.allocation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the {@link User} class, focusing on the creation
 * of user instances and the correct handling of user properties.
 */
public class UserTest {

    /**
     * Validates the creation of a {@link User} instance using its constructor
     * and ensures the correct assignment of all provided user details.
     */
    @Test
    void createUserWithConstructorAndValidateFields() {
        String username = "testUser";
        String password = "testPass";
        String firstname = "Test";
        String lastname = "User";
        String email = "test@gmail.com";
        User user = new User(username, password, firstname, lastname, email);

        assertNotNull(user, "User should not be null");
        assertEquals(username, user.getUsername(), "Username should match the one provided to the constructor");
        assertEquals(password, user.getPassword(), "Password should match the one provided to the constructor");
        assertEquals(firstname, user.getFirstName(), "First name should match the one provided to the constructor");
        assertEquals(lastname, user.getLastName(), "Last name should match the one provided to the constructor");
    }

    /**
     * Tests the ability to update a {@link User}'s password and validates that
     * the new password is correctly set and retrievable.
     */
    @Test
    void setPasswordAndValidate() {
        User user = new User();
        String newPassword = "newPassword";
        user.setPassword(newPassword);

        assertEquals(newPassword, user.getPassword(), "Password should be updated to the new value");
    }
}
