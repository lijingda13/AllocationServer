package com.project.allocation.service.impl;

import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
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
        return List.of();
    }

    @Override
    public List<StudentProjectDTO> listAvailableProjects(Long userId) {
        List<Project> availableProjects = projectRepository.findAllByStatus(true);
        User student = userRepository.findById(userId).orElse(null);

        return availableProjects.stream().map(project -> {
            StudentProjectDTO dto = new StudentProjectDTO();
            BeanUtils.copyProperties(project, dto);

            boolean isRegistered = interestRecordRepository.existsByStudentAndProject(student, project);
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
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        if (project == null || project.getId() == null) {
            return null;
        }
        Optional<Project> existingProjectOptional = projectRepository.findById(project.getId());
        if (existingProjectOptional.isEmpty()) {
            return null;
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
            return false;
        }
        Project project = projectOptional.get();
        if (project.getStatus()) {
            return false;
        }
        interestRecordRepository.deleteByProjectId(projectId);
        projectRepository.delete(project);
        return true;
    }

    @Override
    public boolean registerInterest(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        User student = userRepository.findById(userId).orElse(null);

        if (project == null || student == null) {
            return false;
        }
        boolean exists = interestRecordRepository.existsByStudentAndProject(student, project);
        if (exists) {
            return false;
        }
        boolean isAssigned = assignRecordRepository.existsByStudentId(userId);
        if (isAssigned) {
            return false;
        }
        InterestRecord interestRecord = new InterestRecord();
        interestRecord.setStudent(student);
        interestRecord.setProject(project);
        interestRecordRepository.save(interestRecord);
        return true;
    }

    @Transactional
    @Override
    public boolean assignProject(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        User student = userRepository.findById(userId).orElse(null);
        if (project == null || student == null || project.getStatus() || assignRecordRepository.findByStudentId(userId).isPresent()) {
            return false;
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
            return null;
        }
        return assignRecord.getProject();
    }
}
