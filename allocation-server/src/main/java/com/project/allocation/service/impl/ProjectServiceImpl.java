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
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<Project> listAllProjects() {
        return projectRepository.findAll();
    }

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

    @Override
    public Project createProject(Project project, Long staffId) {
        User staff = userRepository.findById(staffId).orElse(null);
        if (staff == null) {
            throw new EntityNotFoundException("User not found.");
        }
        project.setStaff(staff);
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        if (project == null || project.getId() == null) {
            throw new EntityNotFoundException("Project not found.");
        }
        Optional<Project> existingProjectOptional = projectRepository.findById(project.getId());
        if (existingProjectOptional.isEmpty()) {
            throw new EntityNotFoundException("Project not found.");
        }
        Project updatedProject = existingProjectOptional.get();
        updatedProject.setTitle(project.getTitle());
        updatedProject.setDescription(project.getDescription());
        return projectRepository.save(updatedProject);
    }

    @Override
    public boolean deleteProject(Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isEmpty()) {
            throw new EntityNotFoundException("Project not found.");
        }
        Project project = projectOptional.get();
        if (project.getStatus()) {
            throw new DataIntegrityViolationException("Project already assigned to a student.");
        }
        interestRecordRepository.deleteByProjectId(projectId);
        projectRepository.delete(project);
        return true;
    }

    @Override
    public boolean registerInterest(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            throw new EntityNotFoundException("Project not found.");
        }
        User student = userRepository.findById(userId).orElse(null);
        if (student == null) {
            throw new EntityNotFoundException("User not found.");
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

    @Override
    public boolean unregisterInterest(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            throw new EntityNotFoundException("Project not found.");
        }
        User student = userRepository.findById(userId).orElse(null);
        if (student == null) {
            throw new EntityNotFoundException("User not found.");
        }
        boolean isAssigned = assignRecordRepository.existsByStudentId(userId);
        if (isAssigned) {
            throw new DataIntegrityViolationException("Student already assigned to a project.");
        }
        if (project.getStatus()) {
            throw new DataIntegrityViolationException("Project already assigned to a student.");
        }
        Optional<InterestRecord> interestRecordOptional = interestRecordRepository.findByStudentIdAndProjectId(userId, projectId);
        if (interestRecordOptional.isEmpty()) {
            throw new EntityNotFoundException("Interest not registered.");
        }
        interestRecordRepository.delete(interestRecordOptional.get());
        return true;
    }

    @Transactional
    @Override
    public boolean assignProject(Long projectId, User user) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            throw new EntityNotFoundException("Project not found.");
        }
        User student = userRepository.findById(user.getId()).orElse(null);
        if (student == null) {
            throw new EntityNotFoundException("User not found.");
        }
        if (project.getStatus()) {
            throw new DataIntegrityViolationException("Project already assigned to a student.");
        }
        if (assignRecordRepository.findByStudentId(user.getId()).isPresent()) {
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

    @Override
    public Project getAssignedProject(Long studentId) {
        AssignRecord assignRecord = assignRecordRepository.findByStudentId(studentId).orElse(null);
        if (assignRecord == null) {
            throw new EntityNotFoundException("Student not assigned");
        }
        return assignRecord.getProject();
    }
}
