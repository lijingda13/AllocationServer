package com.project.allocation.util;

import com.project.allocation.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(SecurityConfig.class)
public class PasswordTest {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void PasswordEncoder() {
        String rawPassword = "123456";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertNotEquals(rawPassword, encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
