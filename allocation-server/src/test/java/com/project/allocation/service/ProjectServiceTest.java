package com.project.allocation.service;

import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.model.AssignRecord;
import com.project.allocation.model.InterestRecord;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import com.project.allocation.repository.AssignRecordRepository;
import com.project.allocation.repository.InterestRecordRepository;
import com.project.allocation.repository.ProjectRepository;
import com.project.allocation.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    private ProjectService projectService;

    @MockBean
    private ProjectRepository projectRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private InterestRecordRepository interestRecordRepository;

    @MockBean
    private AssignRecordRepository assignRecordRepository;
    private Project project1;
    private Project project2;
    private Project project3;
    private User staff;
    private User student1;
    private User student2;
    private InterestRecord interestRecord1;
    private InterestRecord interestRecord2;
    private AssignRecord assignRecord;

    @BeforeEach
    public void setUp() {
        staff = new User();
        staff.setId(1L);
        staff.setUsername("testStaff");
        staff.setPassword("staffPassword");
        staff.setFirstName("staffFirstName");
        staff.setLastName("staffLastName");
        staff.setRole(User.Role.STAFF);

        student1 = new User();
        student1.setId(2L);
        student1.setUsername("testStudent1");
        student1.setPassword("student1Password");
        student1.setFirstName("student1FirstName");
        student1.setLastName("student1LastName");
        student1.setRole(User.Role.STUDENT);

        student2 = new User();
        student2.setId(3L);
        student2.setUsername("testStudent2");
        student2.setPassword("student2Password");
        student2.setFirstName("student2FirstName");
        student2.setLastName("student2LastName");
        student2.setRole(User.Role.STUDENT);

        project1 = new Project();
        project1.setId(1L);
        project1.setTitle("Project 1");
        project1.setDescription("Description 1");
        project1.setStaff(staff);
        project1.setStatus(true);

        project2 = new Project();
        project2.setId(2L);
        project2.setTitle("Project 2");
        project2.setDescription("Description 2");
        project2.setStaff(staff);
        project2.setStatus(false);

        project3 = new Project();
        project3.setId(3L);
        project3.setTitle("Project 3");
        project3.setDescription("Description 3");
        project3.setStaff(staff);
        project3.setStatus(false);

        interestRecord1 = new InterestRecord();
        interestRecord1.setStudent(student1);
        interestRecord1.setProject(project1);

        interestRecord2 = new InterestRecord();
        interestRecord2.setStudent(student2);
        interestRecord2.setProject(project2);

        assignRecord = new AssignRecord();
        assignRecord.setProject(project1);
        assignRecord.setStudent(student1);
    }

    @Test
    public void testListAllProjects() {
        when(projectRepository.findAll()).thenReturn(Collections.emptyList());
        List<Project> projects = projectService.listAllProjects();
        assertNotNull(projects);
        assertTrue(projects.isEmpty());
        verify(projectRepository).findAll();
    }

    @Test
    public void listAllProjects_ReturnsAllProjects() {
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));
        List<Project> projects = projectService.listAllProjects();
        assertNotNull(projects);
        assertEquals(2, projects.size());
        assertTrue(projects.contains(project1));
        assertTrue(projects.contains(project2));
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void listAvailableProjects_ReturnsAvailableProjectsWithInterestStatus() {
        long userId = 2L;
        when(projectRepository.findAllByStatus(false)).thenReturn(Arrays.asList(project2, project3));
        when(interestRecordRepository.existsByStudentIdAndProjectId(eq(userId), anyLong())).thenReturn(false).thenReturn(true);
        List<StudentProjectDTO> availableProjects = projectService.listAvailableProjects(userId);
        assertNotNull(availableProjects);
        assertEquals(2, availableProjects.size());
        assertFalse(availableProjects.get(0).isRegisterStatus());
        assertTrue(availableProjects.get(1).isRegisterStatus());
        verify(projectRepository, times(1)).findAllByStatus(false);
        verify(interestRecordRepository, times(2)).existsByStudentIdAndProjectId(eq(userId), anyLong());
    }

    @Test
    public void listProposedProjects_ReturnsProjectsWithInterestedAndAssignedStudents() {
        long userId = 1L;
        when(projectRepository.findAllByStaffId(userId)).thenReturn(Arrays.asList(project1));
        when(interestRecordRepository.findByProjectId(project1.getId())).thenReturn(Arrays.asList(interestRecord1));
        when(assignRecordRepository.findByProjectId(project1.getId())).thenReturn(Optional.of(assignRecord));

        List<StaffProjectDTO> proposedProjects = projectService.listProposedProjects(userId);

        assertNotNull(proposedProjects);
        assertEquals(1, proposedProjects.size());
        assertEquals(1, proposedProjects.get(0).getInterestStudents().size());
        assertNotNull(proposedProjects.get(0).getAssignedStudent());

        verify(projectRepository, times(1)).findAllByStaffId(userId);
        verify(interestRecordRepository, times(1)).findByProjectId(project1.getId());
        verify(assignRecordRepository, times(1)).findByProjectId(project1.getId());
    }

    @Test
    public void createProject_SavesProjectWithStaff() {
        when(userRepository.findById(staff.getId())).thenReturn(Optional.of(staff));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> {
            Project project = invocation.getArgument(0);
            Project savedProject = new Project(project.getTitle(), project.getDescription(), project.getStaff(), project.getStatus());
            savedProject.setId(1L);
            return savedProject;
        });
        Project createdProject = projectService.createProject(new Project("New Project", "New Description", staff, false), staff.getId());
        assertNotNull(createdProject);
        assertEquals("New Project", createdProject.getTitle());
        assertEquals(staff, createdProject.getStaff());
        verify(userRepository, times(1)).findById(staff.getId());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void updateProject_UpdatesAndSavesProject() {
        when(projectRepository.findById(project1.getId())).thenReturn(Optional.of(project1));
        when(projectRepository.save(any(Project.class))).thenReturn(project1);
        Project updatedProject = projectService.updateProject(project1);
        assertNotNull(updatedProject);
        assertEquals("Project 1", updatedProject.getTitle());
        verify(projectRepository, times(1)).findById(project1.getId());
        verify(projectRepository, times(1)).save(any(Project.class));
    }

    @Test
    public void deleteProject_DeletesProjectIfNotAssigned() {
        when(projectRepository.findById(project2.getId())).thenReturn(Optional.of(project2));
        boolean result = projectService.deleteProject(project2.getId());
        assertTrue(result);
        verify(projectRepository, times(1)).findById(project2.getId());
        verify(interestRecordRepository, times(1)).deleteByProjectId(project2.getId());
        verify(projectRepository, times(1)).delete(project2);
    }

    @Test
    public void registerInterest_RegistersInterestForStudent() {
        when(projectRepository.findById(project2.getId())).thenReturn(Optional.of(project2));
        when(userRepository.findById(student1.getId())).thenReturn(Optional.of(student1));
        when(interestRecordRepository.existsByStudentIdAndProjectId(student1.getId(), project2.getId())).thenReturn(false);
        when(assignRecordRepository.existsByStudentId(student1.getId())).thenReturn(false);
        boolean result = projectService.registerInterest(project2.getId(), student1.getId());
        assertTrue(result);
        verify(interestRecordRepository, times(1)).save(any(InterestRecord.class));
    }

    @Test
    public void unregisterInterest_UnregistersInterestForStudent() {
        when(projectRepository.findById(project2.getId())).thenReturn(Optional.of(project2));
        when(userRepository.findById(student1.getId())).thenReturn(Optional.of(student1));
        when(interestRecordRepository.existsByStudentIdAndProjectId(student1.getId(), project2.getId())).thenReturn(true);
        when(assignRecordRepository.existsByStudentId(student1.getId())).thenReturn(false);
        when(interestRecordRepository.findByStudentIdAndProjectId(student1.getId(), project2.getId())).thenReturn(Optional.of(interestRecord1));
        boolean result = projectService.unregisterInterest(project2.getId(), student1.getId());
        assertTrue(result);
        verify(interestRecordRepository, times(1)).delete(interestRecord1);
    }

    @Test
    public void assignProject_AssignsProjectToStudent() {
        when(projectRepository.findById(project2.getId())).thenReturn(Optional.of(project2));
        when(userRepository.findById(student1.getId())).thenReturn(Optional.of(student1));
        when(assignRecordRepository.findByStudentId(student1.getId())).thenReturn(Optional.empty());
        boolean result = projectService.assignProject(project2.getId(), student1.getId());
        assertTrue(result);
        verify(assignRecordRepository, times(1)).save(any(AssignRecord.class));
        verify(projectRepository, times(1)).save(project2);
    }
}
