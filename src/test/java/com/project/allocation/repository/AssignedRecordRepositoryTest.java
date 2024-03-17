package com.project.allocation.repository;

import com.project.allocation.model.AssignRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class AssignedRecordRepositoryTest {

    @Autowired
    private AssignedRecordRepository assignedRecordRepository;

    @Test
    public void testGetAssignedByStudentId() {
        Optional<AssignRecord> assignRecord = assignedRecordRepository.findByStudentId(3L);
        assertNotEquals(Optional.empty(), assignRecord);
    }

    @Test
    public void testGetAssignedByProjectId() {
        Optional<AssignRecord> assignRecord = assignedRecordRepository.findByProjectId(1L);
        assertNotEquals(Optional.empty(), assignRecord);
    }

    @Test
    public void testProjectHasBeenAssignedByProjectId() {
        boolean exists = assignedRecordRepository.existsByProjectId(1L);
        assertNotEquals(false, exists);
    }

    @Test
    public void testProjectHasBeenAssignedByStudentUserId() {
        boolean exists = assignedRecordRepository.existsByStudentId(3L);
        assertNotEquals(false, exists);
    }
}
