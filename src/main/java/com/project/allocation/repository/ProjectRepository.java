package com.project.allocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.allocation.model.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    // You can define custom queries here if needed
}