package com.project.allocation.repository;

import com.project.allocation.model.InterestedRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestedRecordRepository extends JpaRepository<InterestedRecord, Long> {
}
