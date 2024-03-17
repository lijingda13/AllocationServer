package com.project.allocation.repository;

import com.project.allocation.model.InterestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestedRecordRepository extends JpaRepository<InterestRecord, Long> {

    List<InterestRecord> findByStudentId(long studentId);

    List<InterestRecord> findByProjectId(long projectId);

}
