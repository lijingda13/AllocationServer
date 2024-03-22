package com.project.allocation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.allocation.config.JwtFilter;
import com.project.allocation.config.SecurityConfig;
import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.model.User;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.AuthService;
import com.project.allocation.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class AuthControllerTest {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthController authController;

    @Test
    public void testSuccessfulLogin(){
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
