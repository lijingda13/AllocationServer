package com.project.allocation.repository;

import com.project.allocation.model.InterestRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test suite for {@link InterestRecordRepository} focusing on database interactions
 * related to interest records.
 */
@DataJpaTest
public class InterestedRecordRepositoryTest {

    @Autowired
    private InterestRecordRepository interestRecordRepository;

    /**
     * Tests retrieval of interest records by student ID.
     */
    @Test
    public void testGetInterestedByStudentId() {
        List<InterestRecord> interestRecords = interestRecordRepository.findByStudentId(3);
        assertNotEquals(0, interestRecords.size());
    }

    /**
     * Tests retrieval of interest records by project ID.
     */
    @Test
    public void testGetInterestedByProjectId() {
        List<InterestRecord> interestRecords = interestRecordRepository.findByProjectId(1);
        assertNotEquals(0, interestRecords.size());
    }
}
