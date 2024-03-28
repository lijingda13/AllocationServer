package com.project.allocation.repository;


import com.project.allocation.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for managing {@link Project} entities. Provides methods to query projects
 * based on their status or the staff member who proposed them.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByStatus(boolean status);

    List<Project> findAllByStaffId(long id);
}