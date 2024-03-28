package com.project.allocation.repository;

import com.project.allocation.model.InterestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link InterestRecord} entities. Provides methods to query
 * interest records based on students and projects, and to manage these records.
 */
@Repository
public interface InterestRecordRepository extends JpaRepository<InterestRecord, Long> {
    boolean existsByStudentIdAndProjectId(long studentId, long projectId);

    Optional<InterestRecord> findByStudentIdAndProjectId(long studentId, long projectId);

    void deleteByProjectId(long projectId);

    List<InterestRecord> findByStudentId(long studentId);

    List<InterestRecord> findByProjectId(long projectId);

}
