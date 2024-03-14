package com.project.allocation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    void createUserWithConstructorAndValidateFields() {
        String username = "testUser";
        String password = "testPass";
        String firstname = "Test";
        String lastname = "User";
        User user = new User(username, password, firstname, lastname);

        assertNotNull(user, "User should not be null");
        assertEquals(username, user.getUsername(), "Username should match the one provided to the constructor");
        assertEquals(password, user.getPassword(), "Password should match the one provided to the constructor");
        assertEquals(firstname, user.getFirstName(), "First name should match the one provided to the constructor");
        assertEquals(lastname, user.getLastName(), "Last name should match the one provided to the constructor");
    }

    @Test
    void setPasswordAndValidate() {
        User user = new User();
        String newPassword = "newPassword";
        user.setPassword(newPassword);

        assertEquals(newPassword, user.getPassword(), "Password should be updated to the new value");
    }
}
