package com.project.allocation.service.impl;

import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.exception.DataIntegrityViolationException;
import com.project.allocation.model.AssignRecord;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import com.project.allocation.model.InterestRecord;
import com.project.allocation.repository.AssignRecordRepository;
import com.project.allocation.repository.InterestRecordRepository;
import com.project.allocation.repository.ProjectRepository;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing projects.
 * Provides functionality to list, create, update, delete, register interest in, unregister interest from,
 * and assign projects.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final InterestRecordRepository interestRecordRepository;
    private final AssignRecordRepository assignRecordRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository, InterestRecordRepository interestRecordRepository, AssignRecordRepository assignRecordRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.interestRecordRepository = interestRecordRepository;
        this.assignRecordRepository = assignRecordRepository;
    }

    /**
     * Lists all projects.
     *
     * @return a list of all projects
     */
    @Override
    public List<Project> listAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Lists available projects for a student.
     *
     * @param userId the ID of the student
     * @return a list of StudentProjectDTOs representing available projects for the student
     */
    @Override
    public List<StudentProjectDTO> listAvailableProjects(Long userId) {
        List<Project> availableProjects = projectRepository.findAllByStatus(false);
        return availableProjects.stream().map(project -> {
            StudentProjectDTO dto = new StudentProjectDTO();
            BeanUtils.copyProperties(project, dto);
            boolean isRegistered = interestRecordRepository.existsByStudentIdAndProjectId(userId, project.getId());
            dto.setRegisterStatus(isRegistered);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * Lists proposed projects by a staff member.
     *
     * @param userId the ID of the staff member
     * @return a list of StaffProjectDTOs representing projects proposed by the staff member
     */
    @Override
    public List<StaffProjectDTO> listProposedProjects(Long userId) {
        List<Project> projects = projectRepository.findAllByStaffId(userId);
        List<StaffProjectDTO> staffProjectDTOs = new ArrayList<>();

        for (Project project : projects) {
            StaffProjectDTO dto = new StaffProjectDTO();
            BeanUtils.copyProperties(project, dto);

            List<InterestRecord> interestRecords = interestRecordRepository.findByProjectId(project.getId());
            List<User> interestedStudents = interestRecords.stream()
                    .map(InterestRecord::getStudent)
                    .collect(Collectors.toList());
            dto.setInterestStudents(interestedStudents);

            assignRecordRepository.findByProjectId(project.getId()).ifPresent(assignRecord -> dto.setAssignedStudent(assignRecord.getStudent()));
            staffProjectDTOs.add(dto);
        }
        return staffProjectDTOs;
    }

    /**
     * Creates a new project associated with a staff member.
     *
     * @param project the project to be created
     * @param staffId the ID of the staff member proposing the project
     * @return the created project
     */
    @Override
    public Project createProject(Project project, Long staffId) {
        User staff = userRepository.findById(staffId).orElse(null);
        if (staff == null) {
            throw new NullPointerException("User not found.");
        }
        project.setStaff(staff);
        return projectRepository.save(project);
    }

    /**
     * Updates an existing project.
     *
     * @param project the project with updated information
     * @return the updated project
     */
    @Override
    public Project updateProject(Project project) {
        if (project == null || project.getId() == null) {
            throw new NullPointerException("Project not found.");
        }
        Optional<Project> existingProjectOptional = projectRepository.findById(project.getId());
        if (existingProjectOptional.isEmpty()) {
            throw new NullPointerException("Project not found.");
        }
        Project updatedProject = existingProjectOptional.get();
        updatedProject.setTitle(project.getTitle());
        updatedProject.setDescription(project.getDescription());
        return projectRepository.save(updatedProject);
    }

    /**
     * Deletes a project by its ID.
     *
     * @param projectId the ID of the project to be deleted
     * @return true if the project was deleted, false otherwise
     */
    @Transactional
    @Override
    public boolean deleteProject(Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            throw new NullPointerException("Project not found.");
        }
        Project project = projectOptional.get();
        if (project.getStatus()) {
            throw new DataIntegrityViolationException("Project already assigned to a student.");
        }
        interestRecordRepository.deleteByProjectId(projectId);
        projectRepository.delete(project);
        return true;
    }

    /**
     * Registers a student's interest in a project.
     *
     * @param projectId the ID of the project
     * @param userId    the ID of the student expressing interest
     * @return true if the interest was registered, false otherwise
     */
    @Override
    public boolean registerInterest(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            throw new NullPointerException("Project not found.");
        }
        User student = userRepository.findById(userId).orElse(null);
        if (student == null) {
            throw new NullPointerException("User not found.");
        }
        boolean exists = interestRecordRepository.existsByStudentIdAndProjectId(userId, projectId);
        if (exists) {
            throw new DataIntegrityViolationException("Interest already registered.");
        }
        boolean isAssigned = assignRecordRepository.existsByStudentId(userId);
        if (isAssigned) {
            throw new DataIntegrityViolationException("Student already assigned to a project.");
        }
        if (project.getStatus()) {
            throw new DataIntegrityViolationException("Project already assigned to a student.");
        }
        InterestRecord interestRecord = new InterestRecord();
        interestRecord.setStudent(student);
        interestRecord.setProject(project);
        interestRecordRepository.save(interestRecord);
        return true;
    }

    /**
     * Unregisters a student's interest in a project.
     *
     * @param projectId the ID of the project
     * @param userId    the ID of the student withdrawing interest
     * @return true if the interest was unregistered, false otherwise
     */
    @Override
    public boolean unregisterInterest(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            throw new NullPointerException("Project not found.");
        }
        User student = userRepository.findById(userId).orElse(null);
        if (student == null) {
            throw new NullPointerException("User not found.");
        }
        boolean isAssigned = assignRecordRepository.existsByStudentId(userId);
        if (isAssigned) {
            throw new DataIntegrityViolationException("Student already assigned to a project.");
        }
        if (project.getStatus()) {
            throw new DataIntegrityViolationException("Project already assigned to a student.");
        }
        boolean exists = interestRecordRepository.existsByStudentIdAndProjectId(userId, projectId);
        if (!exists) {
            throw new DataIntegrityViolationException("Interest not registered.");
        }
        InterestRecord interestRecord = interestRecordRepository.findByStudentIdAndProjectId(userId, projectId)
                .orElseThrow(() -> new NullPointerException("Interest record not found."));
        interestRecordRepository.delete(interestRecord);
        return true;
    }

    /**
     * Assigns a project to a student, marking the project as assigned and recording the assignment.
     *
     * @param projectId the ID of the project to be assigned
     * @param userId    the ID of the student to whom the project is assigned
     * @return true if the project was successfully assigned, false otherwise
     */
    @Transactional
    @Override
    public boolean assignProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            throw new NullPointerException("Project not found.");
        }
        User student = userRepository.findById(userId).orElse(null);
        if (student == null) {
            throw new NullPointerException("User not found.");
        }
        if (project.getStatus()) {
            throw new DataIntegrityViolationException("Project already assigned to a student.");
        }
        if (assignRecordRepository.findByStudentId(userId).isPresent()) {
            throw new DataIntegrityViolationException("Student already assigned to a project.");
        }
        AssignRecord assignRecord = new AssignRecord();
        assignRecord.setStudent(student);
        assignRecord.setProject(project);
        assignRecordRepository.save(assignRecord);
        project.setStatus(true);
        projectRepository.save(project);
        return true;
    }
}
