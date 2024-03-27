package com.project.allocation.repository;


import com.project.allocation.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByStatus(boolean status);

    List<Project> findAllByStaffId(long id);
}