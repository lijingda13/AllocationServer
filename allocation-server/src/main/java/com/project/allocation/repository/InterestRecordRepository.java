package com.project.allocation.repository;

import com.project.allocation.model.InterestRecord;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestRecordRepository extends JpaRepository<InterestRecord, Long> {
    boolean existsByStudentIdAndProjectId(long studentId, long projectId);
    Optional<InterestRecord> findByStudentIdAndProjectId(long studentId, long projectId);

    void deleteByProjectId(long projectId);

    List<InterestRecord> findByStudentId(long studentId);

    List<InterestRecord> findByProjectId(long projectId);

}
