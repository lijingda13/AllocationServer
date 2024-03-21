package com.terminal;

import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import com.terminal.util.HttpClientUtil;

public class StaffClient extends UserClient{
    private Scanner scanner = new Scanner(System.in);

    public void showMenu(Long id) throws Exception {
        super.showMenu(id);
        while (true) {
            System.out.println("\nStaff Dashboard:");
            System.out.println("1. View All Projects");
            System.out.println("2. Get Project Information");
            System.out.println("3. Create a New Project");
            System.out.println("4. Assign a Project to a Student");
            System.out.println("5. Change Password");
            System.out.println("6. Change Email");
            System.out.println("7. User Information");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    getAllProjects();
                    break;
                case 2:
                    System.out.print("Enter Project ID for more info: ");
                    String projectIdForInfo = scanner.nextLine();
                    getProjectInformation(projectIdForInfo);
                    break;
                case 3:
                    System.out.print("Enter Title for the new project: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Description for the new project: ");
                    String description = scanner.nextLine();
                    createProject(title, description);
                    break;
                case 4:
                    System.out.print("Enter Project ID to assign: ");
                    String projectIdToAssign = scanner.nextLine();
                    System.out.print("Enter Student ID to assign to the project: ");
                    String studentId = scanner.nextLine();
                    assignProjectToStudent(projectIdToAssign, studentId);
                    break;
                case 5:
                    System.out.print("Enter Password to change: ");
                    String password = scanner.nextLine();
                    changePassword(password);
                    break;
                case 6:
                    System.out.print("Enter Email to change: ");
                    String email = scanner.nextLine();
                    changeEmail(email);
                    break;
                case 7:
                    getUserInformation();
                    break;
                case 8:
                    return;  // Exit the method, leading to logout or program termination
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    public void getAllProjects() throws Exception {
        HttpResponse<String> response = HttpClientUtil.sendGetWithToken("http://example.com/api/projects");
        if (response.statusCode() == 200) {
            JSONArray projects = new JSONArray(response.body());
            System.out.println("Projects List:");
            for (int i = 0; i < projects.length(); i++) {
                JSONObject project = projects.getJSONObject(i);
                printProjectDetails(project);
            }
        } else {
            System.out.println("Failed to get projects.");
        }
    }

    public void getProjectInformation(String projectId) throws Exception {
        HttpResponse<String> response = HttpClientUtil.sendGetWithToken("http://example.com/api/projects/" + projectId);
        if (response.statusCode() == 200) {
            JSONObject project = new JSONObject(response.body());
            String title = project.getString("title");
    
            System.out.println("Project Title: " + title);
            System.out.println("Interested Students:");
    
            JSONArray interestedStudents = project.getJSONArray("interestedStudents");
            for (int i = 0; i < interestedStudents.length(); i++) {
                JSONObject student = interestedStudents.getJSONObject(i);
                Long studentId = student.getLong("id");
                String username = student.getString("username");
                System.out.println(" - ID: " + studentId + ", Username: " + username);
            }
        } else {
            System.out.println("Failed to get project information.");
        }
    }

    public void createProject(String title, String description) throws Exception {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);

        HttpResponse<String> response = HttpClientUtil.sendPostWithToken("http://example.com/api/projects/" + id, json.toString());
        if (response.statusCode() == 201) {
            System.out.println("Project created successfully.");
        } else {
            System.out.println("Failed to create project.");
        }
    }

    public void assignProjectToStudent(String projectId, String studentId) throws Exception {
        JSONObject json = new JSONObject();
        json.put("student_id", studentId);

        HttpResponse<String> response = HttpClientUtil.sendPostWithToken("http://example.com/api/projects/" + projectId + "/assign", json.toString());
        if (response.statusCode() == 200) {
            System.out.println("Project assigned successfully to student ID: " + studentId);
        } else {
            System.out.println("Failed to assign project.");
        }
    }

    private void printProjectDetails(JSONObject project) {
        Long id = project.getLong("id");
        String title = project.getString("title");
        boolean assignStatus = project.getBoolean("assign_status");
        System.out.println("ID: " + id + ", Title: " + title + ", Status: " + (assignStatus ? "Assigned" : "Unassigned"));
    
                // If the project is not assigned, display the interested student's information
        if (!assignStatus && project.has("student")) {
            JSONObject student = project.getJSONObject("student");
            Long studentId = student.getLong("id");
            String username = student.getString("username");
            System.out.println("  - Interested Student ID: " + studentId + ", Username: " + username);
        }
    }
}