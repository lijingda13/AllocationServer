package com.project.allocation.repository;

import com.project.allocation.model.AssignRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing {@link AssignRecord} entities. This interface provides methods
 * for querying assignment records in the database, particularly focusing on records associated
 * with specific students or projects.
 */
@Repository
public interface AssignRecordRepository extends JpaRepository<AssignRecord, Long> {
    Optional<AssignRecord> findByStudentId(long studentId);

    Optional<AssignRecord> findByProjectId(long projectId);

    boolean existsByStudentId(long studentId);

    boolean existsByProjectId(long projectId);

}
