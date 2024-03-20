package com.project.allocation.repository;

import com.project.allocation.model.AssignRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class AssignRecordRepositoryTest {

    @Autowired
    private AssignRecordRepository assignRecordRepository;

    @Test
    public void testGetAssignedByStudentId() {
        Optional<AssignRecord> assignRecord = assignRecordRepository.findByStudentId(3L);
        assertNotEquals(Optional.empty(), assignRecord);
    }

    @Test
    public void testGetAssignedByProjectId() {
        Optional<AssignRecord> assignRecord = assignRecordRepository.findByProjectId(1L);
        assertNotEquals(Optional.empty(), assignRecord);
    }

    @Test
    public void testProjectHasBeenAssignedByProjectId() {
        boolean exists = assignRecordRepository.existsByProjectId(1L);
        assertNotEquals(false, exists);
    }

    @Test
    public void testProjectHasBeenAssignedByStudentUserId() {
        boolean exists = assignRecordRepository.existsByStudentId(3L);
        assertNotEquals(false, exists);
    }
}
