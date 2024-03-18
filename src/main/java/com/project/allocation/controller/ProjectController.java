package com.project.allocation.controller;

import com.project.allocation.service.ProjectService;
import com.project.allocation.service.UserService;
import com.project.allocation.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import com.project.allocation.model.User.Role;
import com.project.allocation.service.impl.ProjectServiceImpl;
import com.project.allocation.service.impl.UserServiceImpl;

import java.util.List;

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

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> listProjects() {
        List<Project> projects = projectService.listAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProject(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return project != null ? new ResponseEntity<>(project, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        Project createdProject = projectService.createProject(project);
        String userId = jwtUtil.getUserIdFromToken(token);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        project.setId(id);
        Project updatedProject = projectService.updateProject(project);
        return updatedProject != null ? new ResponseEntity<>(updatedProject, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        boolean deleted = projectService.deleteProject(id);
        return deleted ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/projects/{projectId}/interest")
    public ResponseEntity<?> registerInterest(@PathVariable Long projectId, @AuthenticationPrincipal User user) {
        boolean registered = projectService.registerInterest(projectId, user.getId());
        return registered ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/projects/{projectId}/assign")
    public ResponseEntity<?> assignProject(@PathVariable Long projectId, @RequestParam Long userId) {
        boolean assigned = projectService.assignProject(projectId, userId);
        return assigned ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
