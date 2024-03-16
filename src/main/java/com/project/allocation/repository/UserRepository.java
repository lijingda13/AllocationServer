package com.project.allocation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.allocation.model.User;

import jakarta.persistence.EntityNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); // No user found with the given ID
        }
    }

    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); // No user found with the given username
        }
    }

    public User save(User user) {
        if(user.getId() == null) {
            // Insert
            String sql = "INSERT INTO users (username, role, first_name, last_name, password) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getUsername(), user.getRole().ordinal(), user.getFirstName(), user.getLastName(), user.getPassword());
        } else {
            // Update
            String sql = "UPDATE users SET username = ?, role = ?, first_name = ?, last_name = ?, password = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getUsername(), user.getRole().ordinal(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getId());
        }
        // After saving, return the user object (potentially with ID updated if it was an insert)
        return findByUsername(user.getUsername()).orElseThrow(() -> new EntityNotFoundException("User not saved"));
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Assuming there is a constructor in the User class that sets the username, firstname, and lastname
            User user = new User(
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("first_name"),
                rs.getString("last_name")
            );
            user.setId(rs.getLong("id")); 
            user.setRole(User.Role.values()[rs.getInt("role")]); // Assuming role is stored as an ordinal
            // password should be encrypted and thus should not be set directly from the ResultSet
            return user;
        }
    }
}