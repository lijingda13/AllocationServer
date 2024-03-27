package com.terminal;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
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
            System.out.println("2. Get Proposed Project Information");
            System.out.println("3. Create a New Project");
            System.out.println("4. Assign a Project to a Student");
            System.out.println("5. Change Password");
            System.out.println("6. Change Email");
            System.out.println("7. User Information");
            System.out.println("8. Update Project Information");
            System.out.println("9. Delete Project");
            System.out.println("10. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    getAllProjects();
                    break;
                case 2:
                    System.out.print("Get Proposed Project Information");
                    getProjectInformation(id);
                    break;
                case 3:
                    System.out.print("Enter Title for the new project: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Description for the new project: ");
                    String description = scanner.nextLine();
                    createProject(title, description,id);
                    break;
                case 4:
                    System.out.print("Enter Project ID to assign: ");
                    Long projectIdToAssign = scanner.nextLong();
                    System.out.print("Enter Student ID to assign to the project: ");
                    Long studentId = scanner.nextLong();
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
                    System.out.print("Enter Project Id to change: ");
                    Long projectId = scanner.nextLong();
                    scanner.nextLine();
                    System.out.print("Enter title to change: ");
                    String title1 = scanner.nextLine();
                    System.out.print("Enter description to change: ");
                    String description1 = scanner.nextLine();
                    updateProjectInformation(projectId, title1, description1);
                    break;
                case 9:
                    System.out.print("Enter Project Id to delete: ");
                    Long projectId1 = scanner.nextLong();
                    deleteProject(projectId1);
                    break;
                case 10:
                    return;  // Exit the method, leading to logout or program termination
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    public void getAllProjects() throws Exception {
        HttpResponse<String> response = HttpClientUtil.sendGetWithToken("http://localhost:8080/api/projects");
    
        if (response.statusCode() == 200) {
            JSONArray projects = new JSONArray(response.body());
            System.out.println("Projects List:");
            for (int i = 0; i < projects.length(); i++) {
                JSONObject project = projects.getJSONObject(i);
                printProjectDetails(project);
                System.out.println("----------------------------");
            }
        } else {
            System.out.println("Failed to get projects. Status code: " + response.statusCode());
        }
    }

    public void getProjectInformation(Long staffId) throws Exception {
        HttpResponse<String> response = HttpClientUtil.sendGetWithToken("http://localhost:8080/api/staff/" + staffId + "/proposed-projects");

        if (response.statusCode() == 200) {
            JSONArray projects = new JSONArray(response.body());
            System.out.println("List of Proposed Projects by Staff ID " + staffId + ":");
            for (int i = 0; i < projects.length(); i++) {
                JSONObject project = projects.getJSONObject(i);
                printProjectDetails(project);  // Call your method to print project details
                boolean assignStatus = project.getBoolean("status");
                if (!assignStatus && project.has("interestStudents")) {

                    JSONArray interestedStudents = project.getJSONArray("interestStudents");
                    
                    for (int j = 0; j < interestedStudents.length(); j++) {
                        JSONObject student = interestedStudents.getJSONObject(j);
                        Long studentId = student.getLong("id");
                        String username = student.getString("username");
                        System.out.println("  - Interested Student ID: " + studentId + ", Username: " + username);
                    }
                } 
                else if (assignStatus && project.has("assignedStudent")) {
                    JSONObject student = project.getJSONObject("assignedStudent");
                    Long studentId = student.getLong("id");
                    String username = student.getString("username");
                    System.out.println("  - Assigned Student ID: " + studentId + ", Username: " + username);
                } 
                System.out.println("----------------------------");
            }
        } else if (response.statusCode() == 404) {
            System.out.println("Staff member not found or no projects proposed by the staff.");
        } else {
            System.out.println("Failed to get proposed projects. Status code: " + response.statusCode());
        }
    }

    public void updateProjectInformation(Long projectId, String title, String description) throws Exception {
        // Create a JSON object with the fields that need to be updated
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("description", description);
    
        // Construct the endpoint URL to include the projectId as a path variable
        String endpoint = "http://localhost:8080/api/projects/" + projectId;
    
        // Send the PATCH request with the JWT token and JSON payload
        // Assuming sendPatchWithToken method accepts the URL, JSON payload, and the JWT token
        HttpResponse<String> response = HttpClientUtil.sendPatchWithToken(endpoint, json.toString());
    
        // Handle the response based on the status code
        if (response.statusCode() == 200) {
            System.out.println("Project information successfully updated.");
            JSONObject newProject = new JSONObject(response.body());
            printProjectDetails(newProject);
        } else if (response.statusCode() == 404) {
            System.out.println("Project not found.");
        } else {
            System.out.println("Failed to update project information. Status code: " + response.statusCode());
        }
    }

    public void createProject(String title, String description, Long staffId) throws Exception {
        JSONObject json = new JSONObject();
    json.put("title", title);
    json.put("description", description);

    // Construct the endpoint URL with the staff ID as a path variable
    String endpoint = "http://localhost:8080/api/staff/" + staffId + "/create-project";

    HttpResponse<String> response = HttpClientUtil.sendPostWithToken(endpoint, json.toString());

    // Handle the response based on the status code
    if (response.statusCode() == 201) {
        System.out.println("Project proposed successfully.");
        JSONObject newProject = new JSONObject(response.body());
        printProjectDetails(newProject);
    } else {
        System.out.println("Failed to propose project. Status code: " + response.statusCode());
    }
}

    public void assignProjectToStudent(Long projectIdToAssign, Long userId) throws Exception {
        JSONObject json = new JSONObject();
        json.put("userId", userId); 

        HttpResponse<String> response = HttpClientUtil.sendPostWithToken("http://localhost:8080/api/projects/" + projectIdToAssign + "/assign-project?userId="+userId, "");

        switch (response.statusCode()) {
            case 200:
                System.out.println("Project ID " + projectIdToAssign + " assigned successfully to student ID: " + userId);
                break;
            case 404:
                System.out.println("Project not found, user not found, or interest record not found.");
                break;
            case 409:
                System.out.println("Project already assigned to another student or student already assigned to another project.");
                break;
            default:
                System.out.println("Failed to assign project. Status code: " + response.statusCode());
                break;
        }
    }
    
    public void deleteProject(Long projectId) throws Exception {
        HttpResponse<String> response = HttpClientUtil.sendDeleteWithToken("http://localhost:8080/api/projects/" + projectId);
    
        if (response.statusCode() == 200) {
            System.out.println("Project successfully deleted.");
        } else if (response.statusCode() == 404) {
            System.out.println("Project not found.");
        } else if (response.statusCode() == 409) {
            System.out.println("Project already assigned and cannot be deleted.");
        }else {
            System.out.println("Failed to delete project. Status code: " + response.statusCode());
        }
    }

    private void printProjectDetails(JSONObject project) {
        Long id = project.getLong("id");
        String title = project.getString("title");
        boolean assignStatus = project.getBoolean("status");
        System.out.println("ID: " + id + ", Title: " + title + ", Status: " + (assignStatus ? "Unavailable" : "Available"));
        
    System.out.println("Create Date: " + project.getString("createDate"));
    }
}