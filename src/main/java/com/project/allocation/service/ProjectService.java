package com.project.allocation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import java.util.List;

@Service
public class ProjectService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Project createProject(Project project) {
        final String sql = "INSERT INTO projects (title, description, status, staff_user_id, create_time) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                project.getTitle(),
                project.getDescription(),
                project.getStatus(),
                project.getStaff().getId()
        );
        return project; // In a real application, you would return the Project with the generated ID
    }

    public List<Project> getAllProjects() {
        final String sql = "SELECT * FROM projects";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Project project = new Project();
            project.setId(rs.getLong("id"));
            project.setTitle(rs.getString("title"));
            project.setDescription(rs.getString("description"));
            project.setStatus(rs.getBoolean("status"));
            // You would also need to fetch and set the staff member for each project
            return project;
        });
    }

    public void assignProject(Project project, User student) {
        final String sql = "UPDATE projects SET student_user_id = ?, status = ? WHERE id = ?";
        jdbcTemplate.update(sql, student, false, project);
        // Consider additional logic for managing the assigned list
    }

    public void registerInterest(Project project, User user) {
        final String sql = "INSERT INTO interest_list (project_id, student_user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, project, user);
        // You must also handle the case where the student has already registered interest
    }

    public Project assignProject(Project project, User student, User staff) {
        if (project.getStaff().getId().equals(staff.getId())) {
            // Update the project with the student's ID and set the status to 'assigned'
            String sql = "UPDATE projects SET student_user_id = ?, status = ? WHERE id = ?";
            jdbcTemplate.update(sql, student.getId(), false, project.getId());
            
            // Add an entry in the assigned list table
            String insertSql = "INSERT INTO assigned_list (project_id, student_user_id, assign_time) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertSql, project.getId(), student.getId());
            
            // Update the project object and return it
            project.setAssignedStudent(student);
            project.setStatus(false);
            return project;
        } else {
            throw new IllegalArgumentException("This staff cannot assign the project.");
        }
    }
    
    public Project proposeProject(Project project, User user) {
        // Validate that the user is indeed a Staff member
        if (user.getRole() == User.Role.STAFF) {
            // Insert the new project into the projects table
            String sql = "INSERT INTO projects (title, description, status, staff_user_id, create_time) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    project.getTitle(),
                    project.getDescription(),
                    true,
                    user.getId());
            
            // Update the project object with the new ID (this would require fetching the new ID from the database, not shown here)
            project.setStaff(user);
            project.setStatus(true);
            return project;
        } else {
            throw new IllegalArgumentException("Only staff members can propose projects.");
        }
    }

}
