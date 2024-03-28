package com.project.allocation.repository;

import com.project.allocation.model.AssignRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Test suite for {@link AssignRecordRepository} focusing on database interactions
 * related to assignment records.
 */
@DataJpaTest
public class AssignRecordRepositoryTest {

    @Autowired
    private AssignRecordRepository assignRecordRepository;

    /**
     * Tests retrieval of an assignment record by student ID.
     */
    @Test
    public void testGetAssignedByStudentId() {
        Optional<AssignRecord> assignRecord = assignRecordRepository.findByStudentId(3L);
        assertNotEquals(Optional.empty(), assignRecord);
    }

    /**
     * Tests retrieval of an assignment record by project ID.
     */
    @Test
    public void testGetAssignedByProjectId() {
        Optional<AssignRecord> assignRecord = assignRecordRepository.findByProjectId(1L);
        assertNotEquals(Optional.empty(), assignRecord);
    }

    /**
     * Tests existence check for assignment records by project ID.
     */
    @Test
    public void testProjectHasBeenAssignedByProjectId() {
        boolean exists = assignRecordRepository.existsByProjectId(1L);
        assertNotEquals(false, exists);
    }

    /**
     * Tests existence check for assignment records by student ID.
     */
    @Test
    public void testProjectHasBeenAssignedByStudentUserId() {
        boolean exists = assignRecordRepository.existsByStudentId(3L);
        assertNotEquals(false, exists);
    }
}
