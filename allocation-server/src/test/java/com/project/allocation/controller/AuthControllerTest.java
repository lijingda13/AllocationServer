package com.project.allocation.controller;

import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.model.User;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.AuthService;
import com.project.allocation.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This class tests the authentication functionalities provided by the {@link AuthController}.
 * It includes tests for successful and failed login attempts using mock user credentials.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthController authController;

    /**
     * Tests a successful login scenario where valid user credentials are provided.
     * It verifies that the response status is OK, the response body contains a non-null
     * {@link AuthResponseDTO} with a non-null JWT token, user ID, and user details that match the provided credentials.
     */
    @Test
    public void testSuccessfulLogin() {
        User user = new User();
        user.setUsername("rwilliams");
        user.setPassword("123456");

        ResponseEntity<AuthResponseDTO> response = authController.login(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getBody());

        String token = response.getBody().getToken();
        assertNotNull(token);

        Long userId = response.getBody().getUserId();
        assertNotNull(userId);
        assertEquals(1L, userId);

        User u = response.getBody().getUser();
        assertNotNull(u);
        assertNotNull(u.getId());
        assertNotNull(u.getUsername());
        assertEquals(user.getUsername(), u.getUsername());
    }

    /**
     * Tests a failed login scenario where invalid user credentials are provided.
     * It verifies that the response status is BAD_REQUEST and the response body contains a non-null
     * {@link AuthResponseDTO} with an error message indicating an invalid login attempt.
     */
    @Test
    public void testFailedLogin() {
        User user = new User();
        user.setUsername("rwilliams");
        user.setPassword("1234567");

        ResponseEntity<AuthResponseDTO> response = authController.login(user);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("Invalid username or password", response.getBody().getMessage());
    }
}
