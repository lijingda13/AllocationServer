package com.project.allocation.repository;

import com.project.allocation.model.InterestRecord;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRecordRepository extends JpaRepository<InterestRecord, Long> {
    boolean existsByStudentAndProject(User student, Project project);

    List<InterestRecord> findByStudentId(long studentId);

    List<InterestRecord> findByProjectId(long projectId);

}
