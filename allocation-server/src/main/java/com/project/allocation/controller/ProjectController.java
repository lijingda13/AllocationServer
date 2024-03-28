package com.project.allocation.controller;

import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.service.ProjectService;
import com.project.allocation.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.allocation.model.Project;

import java.util.List;

/**
 * The {@code ProjectController} class handles all web requests related to projects,
 * including listing, creating, updating, and deleting projects, as well as managing project interests and assignments.
 */
@RestController
@RequestMapping("/api")
public class ProjectController {

    private final ProjectService projectService;

    private final JwtUtil jwtUtil;

    @Autowired
    public ProjectController(ProjectService projectService, JwtUtil jwtUtil) {
        this.projectService = projectService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Lists all projects.
     *
     * @return A {@link ResponseEntity} containing a list of all projects and {@link HttpStatus#OK}.
     */
    @GetMapping("/projects")
    public ResponseEntity<List<Project>> listAllProjects() {
        List<Project> projects = projectService.listAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Lists all available projects for a given student.
     *
     * @param studentId The ID of the student.
     * @return A {@link ResponseEntity} containing a list of all available projects for the student and {@link HttpStatus#OK}.
     */
    @GetMapping("/students/{studentId}/available-projects")
    public ResponseEntity<List<StudentProjectDTO>> listAvailableProjects(@PathVariable Long studentId) {
        List<StudentProjectDTO> projects = projectService.listAvailableProjects(studentId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Lists all proposed projects by a given staff member.
     *
     * @param staffId The ID of the staff member.
     * @return A {@link ResponseEntity} containing a list of all proposed projects by the staff member and {@link HttpStatus#OK}.
     */
    @GetMapping("/staff/{staffId}/proposed-projects")
    public ResponseEntity<List<StaffProjectDTO>> listProposedProjects(@PathVariable Long staffId) {
        List<StaffProjectDTO> projects = projectService.listProposedProjects(staffId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    /**
     * Creates a new project.
     *
     * @param staffId The ID of the staff member creating the project.
     * @param project The {@link Project} object to be created.
     * @return A {@link ResponseEntity} containing the created project and {@link HttpStatus#CREATED}.
     */
    @PostMapping("/staff/{staffId}/create-project")
    public ResponseEntity<Project> createProject(@PathVariable Long staffId, @Valid @RequestBody Project project) {
        Project createdProject = projectService.createProject(project, staffId);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    /**
     * Updates an existing project.
     *
     * @param projectId The ID of the project to update.
     * @param project   The updated {@link Project} object.
     * @return A {@link ResponseEntity} containing the updated project and {@link HttpStatus#OK}, or {@link HttpStatus#NOT_FOUND} if the project does not exist.
     */
    @PatchMapping("/projects/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Long projectId, @RequestBody Project project) {
        project.setId(projectId);
        Project updatedProject = projectService.updateProject(project);
        return updatedProject != null ? new ResponseEntity<>(updatedProject, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Deletes a project based on the given project ID.
     *
     * @param projectId The ID of the project to delete.
     * @return A {@link ResponseEntity} with a message indicating success or failure and the appropriate HTTP status code.
     */
    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        boolean deleted = projectService.deleteProject(projectId);
        if (deleted) {
            return ResponseEntity.ok("Project deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found.");
        }
    }

    /**
     * Registers interest for a project by a student.
     *
     * @param projectId The ID of the project for which interest is being registered.
     * @param token     The JWT token used for identifying the student.
     * @return A {@link ResponseEntity} indicating success or failure of interest registration along with the appropriate HTTP status code.
     */
    @PostMapping("/projects/{projectId}/register-interest")
    public ResponseEntity<?> registerInterest(@PathVariable Long projectId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        boolean registered = projectService.registerInterest(projectId, userId);
        if (registered) {
            return ResponseEntity.ok("Interest registered successfully");
        } else {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Unregisters interest for a project by a student.
     *
     * @param projectId The ID of the project for which interest is being unregistered.
     * @param token     The JWT token used for identifying the student.
     * @return A {@link ResponseEntity} indicating success or failure of unregistering interest along with the appropriate HTTP status code.
     */
    @PostMapping("/projects/{projectId}/unregister-interest")
    public ResponseEntity<?> unregisterInterest(@PathVariable Long projectId, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Long userId = jwtUtil.getUserIdFromToken(token);
        boolean unregistered = projectService.unregisterInterest(projectId, userId);
        if (unregistered) {
            return ResponseEntity.ok("Unregister interest successfully");
        } else {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Assigns a project to a student based on the provided project ID and student user ID.
     *
     * @param projectId The ID of the project to be assigned.
     * @param userId    The user ID of the student to whom the project is being assigned.
     * @return A {@link ResponseEntity} with a message indicating the success of the assignment along with the appropriate HTTP status code.
     */
    @PostMapping("/projects/{projectId}/assign-project")
    public ResponseEntity<?> assignProject(@PathVariable Long projectId, @RequestParam Long userId) {
        boolean assigned = projectService.assignProject(projectId, userId);
        if (assigned) {
            return ResponseEntity.ok("Project successfully assigned.");
        } else {
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
    }
}
