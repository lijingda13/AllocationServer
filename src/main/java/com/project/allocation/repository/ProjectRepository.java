package com.project.allocation.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.project.allocation.model.Project;
import com.project.allocation.model.User;

import jakarta.persistence.EntityNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Project> findById(Long id) {
        String sql = "SELECT * FROM projects WHERE id = ?";
        try {
            Project project = jdbcTemplate.queryForObject(sql, new ProjectRowMapper(), id);
            return Optional.ofNullable(project);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty(); // In case no project is found with the given ID
        }
    }

    public List<Project> findAll() {
        String sql = "SELECT * FROM projects";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    public Project save(Project project) {
        if(project.getId() == null) {
            // Insert
            String sql = "INSERT INTO projects (staff_user_id, title, description, status, create_time) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, project.getStaff().getId(), project.getTitle(), project.getDescription(), project.getStatus());
        } else {
            // Update
            String sql = "UPDATE projects SET staff_user_id = ?, title = ?, description = ?, status = ?, create_time = ? WHERE id = ?";
            jdbcTemplate.update(sql, project.getStaff().getId(), project.getTitle(), project.getDescription(), project.getStatus(), project.getId());
        }
        // After saving, return the project object (potentially with ID updated if it was an insert)
        return findById(project.getId()).orElseThrow(() -> new EntityNotFoundException("Project not saved"));
    }

    private static class ProjectRowMapper implements RowMapper<Project> {
        @Override
        public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
            Project project = new Project();
            project.setId(rs.getLong("id"));
            // Assuming you have a method to retrieve Staff by their ID, otherwise, you'll need to fetch the related User here.
            User staff = new User(); // Replace with actual user retrieval logic
            staff.setId(rs.getLong("staff_user_id"));
            project.setStaff(staff);
            project.setTitle(rs.getString("title"));
            project.setDescription(rs.getString("description"));
            project.setStatus(rs.getBoolean("status"));
            return project;
        }
    }
}