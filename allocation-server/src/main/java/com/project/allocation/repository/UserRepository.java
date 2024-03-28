package com.project.allocation.repository;


import com.project.allocation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link User} entities. Provides methods to query users based on their
 * username or ID and to check the existence of a user by username.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findById(long id);

    boolean existsByUsername(String username);
}