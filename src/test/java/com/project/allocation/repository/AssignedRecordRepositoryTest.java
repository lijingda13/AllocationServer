package com.project.allocation.repository;

import com.project.allocation.model.AssignRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class AssignedRecordRepositoryTest {

    @Autowired
    private AssignedRecordRepository assignedRecordRepository;

    @Test
    public void testGetAssignedRecords() {
        List<AssignRecord> assignedRecords = assignedRecordRepository.findAll();
        assertNotEquals(0, assignedRecords.size());
    }
}
