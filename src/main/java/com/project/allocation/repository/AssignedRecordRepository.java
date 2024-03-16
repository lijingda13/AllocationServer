package com.project.allocation.repository;

import com.project.allocation.model.AssignRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignedRecordRepository extends JpaRepository<AssignRecord, Long> {
    List<AssignRecord> findByProjectId(Long projectId);

    List<AssignRecord> findByStudentId(Long studentId);
}
