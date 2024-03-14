package com.project.allocation.service;

import java.util.List;

import com.project.allocation.model.Project;
import com.project.allocation.model.Staff;
import com.project.allocation.model.Student;
import com.project.allocation.model.User;
import com.project.allocation.model.User.Role;
import com.project.allocation.repository.ProjectRepository;
import com.project.allocation.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

   public Project proposeProject(Project project, User user) {
    // Validate that the user is indeed a Staff member before allowing them to propose a project
    if (user instanceof Staff && user.getRole() == Role.STAFF) {
        // Set the staff member who is proposing the project
        project.setStaff((Staff) user);
        project.setStatus(true);
        // Save the new project to the repository
        return projectRepository.save(project);
    } else {
        // If the user is not a Staff member, throw an exception
        throw new IllegalArgumentException("Only staff members can propose projects.");
    }
}

    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Transactional
    public Project assignProject(Project project2, User student2, User staffUser) {
        Optional<Project> projectOptional = projectRepository.findById(project2.getId());
        Optional<User> studentOptional = userRepository.findById(student2.getId());

        if (projectOptional.isPresent() && studentOptional.isPresent() && staffUser.getRole()== Role.STAFF) {
            Project project = projectOptional.get();
            User student = studentOptional.get();

            // Logic to ensure the staff member is allowed to assign this project.
            if (project.getStaff().equals(staffUser)) {
                project.setAssignedStudent(student);
                project.setStatus(false); // Assuming 'false' means assigned.
                return projectRepository.save(project);
            } else {
                throw new IllegalArgumentException("This staff cannot assign the project.");
            }
        } else {
            throw new IllegalArgumentException("Project or student not found, or user is not staff.");
        }
    }

    @Transactional
     public void registerInterest(Project newProject, User studentUser) {
        if (!(studentUser instanceof Student)) {
            throw new IllegalArgumentException("Only students can register interest in projects.");
        }

        Student student = (Student) studentUser;
        Project project = projectRepository.findById(newProject.getId())
                .orElseThrow(() -> new IllegalArgumentException("Project with id " + newProject.getId() + " not found"));
        if (!project.getStatus()) {
            throw new IllegalStateException("Cannot register interest in an assigned project.");
        }

        // Check if the student already registered interest
        if (student.getInterestProject().contains(project)) {
            throw new IllegalStateException("Student has already registered interest in this project.");
        }

        student.getInterestProject().add(project);
        userRepository.save(student);
        projectRepository.save(project);
    }

}
