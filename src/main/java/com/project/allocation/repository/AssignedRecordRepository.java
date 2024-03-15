package com.project.allocation.repository;

import com.project.allocation.model.AssignedRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedRecordRepository extends JpaRepository<AssignedRecord, Long> {

}
