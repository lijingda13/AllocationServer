package com.project.allocation.repository;

import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
public class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    private User student;
    private User staff;


    @BeforeEach
    public void setUp() {
        User student = new User();
        student.setUsername("test-student");
        student.setPassword("test-student-password");
        student.setFirstName("test-student-first-name");
        student.setLastName("test-student-last-name");
        student.setRole(User.Role.STUDENT);
        userRepository.save(student);
        this.student = userRepository.findByUsername("test-student");

        User staff = new User();
        staff.setUsername("test-staff");
        staff.setPassword("test-staff-password");
        staff.setFirstName("test-staff-first-name");
        staff.setLastName("test-staff-last-name");
        staff.setRole(User.Role.STAFF);
        userRepository.save(staff);
        this.staff = userRepository.findByUsername("test-staff");
    }


    @Test
    public void testGetProjects() {
        List<Project> projects = projectRepository.findAll();
        assertNotEquals(0, projects.size());
    }


    @Test
    public void testGetProjectById() {
        Optional<Project> project = projectRepository.findById(1L);
        assertNotEquals(Optional.empty(), project);
    }

    @Test
    public void testCreateProject() {
        Project project = new Project();
        project.setTitle("test-project");
        project.setDescription("test-project-description");
        project.setStatus(false);
        project.setStaff(staff);
        projectRepository.save(project);
        assertNotEquals(null, project);

        Optional<Project> project1 = projectRepository.findById(project.getId());
        assertNotEquals(Optional.empty(), project1);
        assertEquals("test-project", project1.get().getTitle());
        assertEquals("test-project-description", project1.get().getDescription());
        assertEquals(false, project1.get().getStatus());
        assertEquals(staff, project1.get().getStaff());
    }

    @Test
    public void testFindAllByStatus() {
        List<Project> projects = projectRepository.findAllByStatus(true);
        assertNotEquals(projects.size(), 0);
    }
}
