package com.project.allocation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.allocation.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom database queries can be added here
}