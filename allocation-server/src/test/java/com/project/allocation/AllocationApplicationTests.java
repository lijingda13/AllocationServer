package com.project.allocation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

/**
 * This class contains tests for verifying the Spring Boot application startup.
 * Specifically, it checks whether the Spring application context loads properly,
 * ensuring that the application configuration is correct and that all required
 * beans are available in the Spring application context.
 */
@SpringBootTest
public class AllocationApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Verifies that the Spring application context loads successfully.
     * A successfully loaded application context indicates that the
     * application configuration is valid and that the Spring framework
     * can manage all beans and dependencies correctly.
     */
    @Test
    void contextLoads() {
        assertNotNull(String.valueOf(applicationContext), "The application context should have been loaded");
    }
}
