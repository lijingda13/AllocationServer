package com.project.allocation.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.dto.StaffProjectDTO;
import com.project.allocation.dto.StudentProjectDTO;
import com.project.allocation.model.Project;
import com.project.allocation.model.User;
import com.project.allocation.repository.AssignRecordRepository;
import com.project.allocation.repository.InterestRecordRepository;
import com.project.allocation.repository.ProjectRepository;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.AuthService;
import com.project.allocation.service.ProjectService;
import com.project.allocation.service.UserService;
import com.project.allocation.service.impl.ProjectServiceImpl;
import com.project.allocation.service.impl.UserServiceImpl;
import com.project.allocation.util.JwtUtil;

import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRecordRepository interestRecordRepository;

    @Autowired
    private AssignRecordRepository assignRecordRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ProjectController projectController;

    private Project project1;
    private Project project2;
    private User staff;
    private User student;
    private String studentToken;
    private String staffToken;
    private static Long projectID;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        AuthResponseDTO staff = authService.login("rwilliams", "123456");
        AuthResponseDTO student = authService.login("jchen", "123456");

        // project1 = new Project();
        // project1.setTitle("Project 1");
        // project1.setDescription("Description 1");
        // project1.setStaff(staff.getUser());
        // project1.setStatus(true);

        // project2 = new Project();
        // project2.setTitle("Project 2");
        // project2.setDescription("Description 2");
        // project2.setStaff(staff.getUser());
        // project2.setStatus(true);


    }

    @Test
    public void testlistAllProject() {
        ResponseEntity<List<Project>> response = projectController.listAllProjects();
        List<Project> projects = response.getBody();
        assertNotNull(projects);
        assertEquals(4, projects.size());
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void testlistAvailableProjects() {
        ResponseEntity<List<StudentProjectDTO>> response = projectController.listAvailableProjects(1L);
        List<StudentProjectDTO> projects = response.getBody();
        assertNotNull(projects);
        assertEquals(3, projects.size());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(!projects.get(0).getStatus());
    }

    @Test
    public void testCreateProjectWithMockMvc() throws Exception {
        String projectJson = "{\"title\":\"Project 1\",\"description\":\"Description 1\"}";
        Long staffId = 1L;
        MvcResult result = mockMvc.perform(post("/api/staff/{staffId}/create-project", staffId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(projectJson)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + staffToken))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.title").value("Project 1"))
        .andExpect(jsonPath("$.description").value("Description 1"))
        .andExpect(jsonPath("$.staff.id").value(staffId))
        .andReturn(); // Captures the response to extract the projectId
        String contentAsString = result.getResponse().getContentAsString();

        // Assuming you have a JSON parsing utility (like Jackson's ObjectMapper) to parse the response
        ObjectMapper objectMapper = new ObjectMapper();
        Project createdProject = objectMapper.readValue(contentAsString, Project.class);

        // Now you have the projectId from the created project
        projectID = createdProject.getId();
    }


    @Test
    public void testCreateProject_ValidationFailure() throws Exception {
        Long staffId = 1L;
        String projectJson = "{\"title\":\"\",\"description\":\"Too short\"}"; // Invalid data

        mockMvc.perform(post("/api/staff/{staffId}/create-project", staffId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(projectJson))
                    .andExpect(status().isBadRequest());
                    
    }

    @Test
public void testUpdateProjectWithMockMvc() throws Exception {
    String updatedProjectJson = "{\"title\":\"Updated Project Title\",\"description\":\"Updated Description\"}";

    when(projectService.updateProject(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

    mockMvc.perform(patch("/api/projects/{projectId}", projectID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(updatedProjectJson)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + staffToken))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Project Title"))
            .andExpect(jsonPath("$.description").value("Updated Description"));

    verify(projectService).updateProject(any(Project.class));
}

@Test
public void testRegisterInterestWithMockMvc() throws Exception {
    // Assume the registerInterest method in the service returns true indicating successful registration
    when(projectService.registerInterest(projectID, anyLong())).thenReturn(true);

    mockMvc.perform(post("/api/projects/{projectId}/register-interest", projectID)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + studentToken)) // Using studentToken assuming a student is registering interest
            .andExpect(status().isOk());

    // Verify that the service method was called with the expected parameters
    verify(projectService).registerInterest(eq(projectID), anyLong());
}

@Test
public void testUnregisterInterestWithMockMvc() throws Exception {
    when(projectService.unregisterInterest(projectID, anyLong())).thenReturn(true);

    mockMvc.perform(post("/api/projects/{projectId}/unregister-interest", projectID)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + studentToken))
            .andExpect(status().isOk());

    verify(projectService).unregisterInterest(eq(projectID), anyLong());
}

@Test
public void testDeleteProjectWithMockMvc() throws Exception {
    when(projectService.deleteProject(projectID)).thenReturn(true);

    mockMvc.perform(delete("/api/projects/{projectId}", projectID)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + staffToken))
            .andExpect(status().isOk())
            .andExpect(content().string(contains("Project deleted successfully")));

    verify(projectService).deleteProject(projectID);
}


    
//    @Test
//    public void testCreateProject() {
//        ResponseEntity<Project> response = projectController.createProject(project1);
//        Project project = response.getBody();
//        assertNotNull(project);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals("Project 1", project.getTitle());
//        assertEquals("Description 1", project.getDescription());
//        assertEquals(5, projectRepository.findAll().size());
//    }

//    @Test
//    public void testUpdateProject() {
//        ResponseEntity<Project> response = projectController.createProject(project2);
//        Project project = response.getBody();
//        assertNotNull(project);
//        project.setTitle("Project 3 Updated");
//        ResponseEntity<Project> updatedResponse = projectController.updateProject(project.getId(), project);
//        Project updatedProject = updatedResponse.getBody();
//        assertNotNull(updatedProject);
//        assertEquals(HttpStatus.OK, updatedResponse.getStatusCode());
//        assertEquals("Project 3 Updated", updatedProject.getTitle());
//    }

}
