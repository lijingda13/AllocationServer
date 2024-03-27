package com.project.allocation.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import com.project.allocation.service.AuthService;
import com.project.allocation.service.ProjectService;
import com.project.allocation.util.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private ProjectService projectService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    private Project project1;
    private Project project2;
    private User student1;
    private User student2;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        AuthResponseDTO staff = authService.login("rwilliams", "123456");
        AuthResponseDTO student = authService.login("jchen", "123456");

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
        project1.setTitle("Title 1");
        project1.setDescription("Description 1");
        project1.setStaff(staff.getUser());
        project1.setStatus(true);

        project2 = new Project();
        project2.setId(2L);
        project2.setTitle("Title 2");
        project2.setDescription("Description 2");
        project2.setStaff(staff.getUser());
        project2.setStatus(false);

        List<Project> projectList = Arrays.asList(project1, project2);
        when(projectService.listAllProjects()).thenReturn(projectList);

    }

    @Test
    public void listAllProjects_ShouldReturnAllProjects() throws Exception {
        List<Project> projects = Arrays.asList(project1, project2);
        when(projectService.listAllProjects()).thenReturn(projects);
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(project1.getTitle())))
                .andExpect(jsonPath("$[1].title", is(project2.getTitle())));
        verify(projectService, times(1)).listAllProjects();
    }

    @Test
    public void listAvailableProjects_ForStudent_ShouldReturnProjects() throws Exception {
        StudentProjectDTO studentProjectDTO1 = new StudentProjectDTO();
        BeanUtils.copyProperties(project1, studentProjectDTO1);
        StudentProjectDTO studentProjectDTO2 = new StudentProjectDTO();
        BeanUtils.copyProperties(project2, studentProjectDTO2);
        List<StudentProjectDTO> studentProjects = Arrays.asList(studentProjectDTO1, studentProjectDTO2);
        Long studentId = 1L;
        when(projectService.listAvailableProjects(studentId)).thenReturn(studentProjects);
        mockMvc.perform(get("/api/students/{studentId}/available-projects", studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(studentProjects.size())));
        verify(projectService, times(1)).listAvailableProjects(studentId);
    }

    @Test
    public void listProposedProjects_ForStaff_ShouldReturnProjects() throws Exception {
        StaffProjectDTO staffProjectDTO1 = new StaffProjectDTO();
        List<User> interestStudents = Arrays.asList(student1, student2);
        BeanUtils.copyProperties(project1, staffProjectDTO1);
        staffProjectDTO1.setInterestStudents(interestStudents);
        staffProjectDTO1.setAssignedStudent(student1);

        StaffProjectDTO staffProjectDTO2 = new StaffProjectDTO();
        BeanUtils.copyProperties(project2, staffProjectDTO2);
        staffProjectDTO2.setInterestStudents(interestStudents);
        staffProjectDTO2.setAssignedStudent(null);

        List<StaffProjectDTO> staffProjects = Arrays.asList(staffProjectDTO1, staffProjectDTO2);

        Long staffId = 1L;
        when(projectService.listProposedProjects(staffId)).thenReturn(staffProjects);

        mockMvc.perform(get("/api/staff/{staffId}/proposed-projects", staffId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(staffProjects.size())));
        verify(projectService, times(1)).listProposedProjects(staffId);
    }

    @Test
    public void createProject_WithValidData_ShouldReturnCreated() throws Exception {
        Long staffId = 1L;
        Project project = new Project();
        project.setTitle("New Title");
        project.setDescription("New Description");
        when(projectService.createProject(any(Project.class), eq(staffId))).thenReturn(project);

        mockMvc.perform(post("/api/staff/{staffId}/create-project", staffId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(project)))
                .andExpect(status().isCreated());
        verify(projectService, times(1)).createProject(any(Project.class), eq(staffId));
    }

    @Test
    public void updateProject_WithValidData_ShouldReturnUpdatedProject() throws Exception {
        Long projectId = 1L;
        Project updatedProject = new Project();
        when(projectService.updateProject(any(Project.class))).thenReturn(updatedProject);

        mockMvc.perform(patch("/api/projects/{projectId}", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedProject)))
                .andExpect(status().isOk());
        verify(projectService, times(1)).updateProject(any(Project.class));
    }

    @Test
    public void deleteProject_ExistingProject_ShouldReturnSuccess() throws Exception {
        Long projectId = 1L;
        when(projectService.deleteProject(projectId)).thenReturn(true);

        mockMvc.perform(delete("/api/projects/{projectId}", projectId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Project deleted successfully")));
        verify(projectService, times(1)).deleteProject(projectId);
    }

    @Test
    public void registerInterest_ShouldReturnOk() throws Exception {
        Long projectId = 1L;
        Long userId = 1L;
        when(jwtUtil.getUserIdFromToken(anyString())).thenReturn(userId);
        when(projectService.registerInterest(projectId, userId)).thenReturn(true);

        mockMvc.perform(post("/api/projects/{projectId}/register-interest", projectId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());
        verify(projectService, times(1)).registerInterest(projectId, userId);
    }

    @Test
    public void unregisterInterest_ShouldReturnOk() throws Exception {
        Long projectId = 1L;
        Long userId = 1L;
        when(jwtUtil.getUserIdFromToken(anyString())).thenReturn(userId);
        when(projectService.unregisterInterest(projectId, userId)).thenReturn(true);

        mockMvc.perform(post("/api/projects/{projectId}/unregister-interest", projectId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer token"))
                .andExpect(status().isOk());
        verify(projectService, times(1)).unregisterInterest(projectId, userId);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
