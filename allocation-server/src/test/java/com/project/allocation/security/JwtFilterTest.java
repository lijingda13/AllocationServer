package com.project.allocation.security;


import com.project.allocation.model.User;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.UserService;
import com.project.allocation.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test suite for {@link JwtFilter} focusing on JWT token validation
 * and authorization for secured endpoints.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class JwtFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilter jwtFilter;

    private User student;

    private User staff;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilters(new JwtFilter(jwtUtil, userRepository))
                .build();
    }

    /**
     * Tests the JWT filter's behavior for the login endpoint.
     */

    @Test
    public void testJwtFilterWithLoginEndpoint() throws Exception {
        String jsonString = "{\"username\":\"rwilliams\",\"password\":\"123456\"}";

        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(jsonString))
                .andExpect(status().isOk());
    }

    /**
     * Tests the JWT filter's behavior for the user registration endpoint.
     */
    @Test
    public void testJwtFilterWithRegistrationEndpoint() throws Exception {
        String jsonString = "{\"username\":\"test\",\"password\":\"123456\",\"firstName\":\"Ricky\",\"lastName\":\"Williams\",\"role\":\"STUDENT\"}";

        mockMvc.perform(post("/api/users")
                        .contentType("application/json")
                        .content(jsonString))
                .andExpect(status().isOk());
    }

    /**
     * Tests accessing a secured endpoint with a valid JWT token should succeed.
     */
    @Test
    public void testJwtFilterWithRightTokenThenSuccess() throws Exception {

        mockMvc.perform(get("/api/users/1")
                        .header("Authorization", "Bearer " + jwtUtil
                                .createToken(userRepository.findByUsername("rwilliams")))
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }
}
