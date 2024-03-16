package com.project.allocation.service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.allocation.model.User;
import com.project.allocation.model.User.Role;

@Service
public class UserService {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        final String sql = "INSERT INTO users (username, role, first_name, last_name, password) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getRole().ordinal(),
                user.getFirstName(),
                user.getLastName(),
                passwordEncoder.encode(user.getPassword())
        );
        return user; // In a real application, you would return the User with the generated ID
    }

    public User updateUser(User userDetails) {
        final String sql = "UPDATE users SET password = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                passwordEncoder.encode(userDetails.getPassword()),
                userDetails.getId()
        );
        return userDetails; // In a real application, you should re-fetch the User to return the updated user
    }

    public boolean login(String role, String username, String password) {
        final String sql = "SELECT password, role FROM users WHERE username = ?";

        try {
            // Use query method with PreparedStatementSetter and ResultSetExtractor
            Boolean result = jdbcTemplate.query(sql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setString(1, username);
                }
            }, new ResultSetExtractor<Boolean>() {
                @Override
                public Boolean extractData(java.sql.ResultSet rs) throws SQLException {
                    if (rs.next()) {
                        String storedPassword = rs.getString("password");
                        String storedRole = rs.getString("role");
                        // Check if the password matches and the role is as expected
                        return storedPassword != null && passwordEncoder.matches(password, storedPassword) && storedRole.equalsIgnoreCase(role);
                    }
                    return false; // Return false if no user is found
                }
            });

            return result != null && result;
        } catch (EmptyResultDataAccessException e) {
            // This exception is thrown if the query did not return any rows, meaning the user was not found
            return false;
        }
    }
}