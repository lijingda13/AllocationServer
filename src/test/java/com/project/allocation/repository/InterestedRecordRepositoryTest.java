package com.project.allocation.repository;

import com.project.allocation.model.InterestRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class InterestedRecordRepositoryTest {

    @Autowired
    private InterestedRecordRepository interestedRecordRepository;

    @Test
    public void testGetInterestedByStudentId() {
        List<InterestRecord> interestRecords = interestedRecordRepository.findByStudentId(3);
        assertNotEquals(0, interestRecords.size());
    }

    @Test
    public void testGetInterestedByProjectId() {
        List<InterestRecord> interestRecords = interestedRecordRepository.findByProjectId(1);
        assertNotEquals(0, interestRecords.size());
    }
}
