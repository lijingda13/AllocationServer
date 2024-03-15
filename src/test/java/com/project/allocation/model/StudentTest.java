package com.project.allocation.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {
    @Test
    void createStudentWithConstructorAndValidateFields() {
        String username = "studentUser";
        String password = "studentPass";
        String firstname = "Student";
        String lastname = "User";
        Student student = new Student(username, password, firstname, lastname);

        assertNotNull(student, "Student should not be null");
        assertEquals(username, student.getUsername(), "Username should match the one provided to the constructor");
        assertEquals(password, student.getPassword(), "Password should match the one provided to the constructor");
        assertEquals(firstname, student.getFirstName(), "First name should match the one provided to the constructor");
        assertEquals(lastname, student.getLastName(), "Last name should match the one provided to the constructor");
        assertEquals(User.Role.STUDENT, student.getRole(), "Role should be STUDENT for a new student created with default constructor");
    }
}
