package com.project.allocation.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StaffTest {
    @Test
    void testProjectsContainProjectWhenProposedByStaff() {
        Staff staff = new Staff("JohnWill", "12345", "John", "Will");
        Project project = new Project();
        staff.proposeProject(project);
        Set<Project> projects = staff.getProjects();
        assertTrue(projects.contains(project));
    }

}
