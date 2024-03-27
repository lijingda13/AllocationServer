package com.project.allocation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

@SpringBootTest
public class AllocationApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        assertNotNull(String.valueOf(applicationContext), "The application context should have been loaded");
    }
}
