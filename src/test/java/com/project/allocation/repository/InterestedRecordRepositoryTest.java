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
    public void testGetInterestedRecords() {
        List<InterestRecord> interestedRecords = interestedRecordRepository.findAll();
        assertNotEquals(0, interestedRecords.size());
    }
}
