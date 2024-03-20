package com.terminal;

import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONObject;

import com.terminal.util.HttpClientUtil;
import org.json.JSONArray;

public class StudentClient extends UserClient{
    private Scanner scanner = new Scanner(System.in);

    public void showMenu() throws Exception {
        while (true) {
            System.out.println("\nStudent Dashboard:");
            System.out.println("1. View All Available Projects");
            System.out.println("2. Register Interest in a Project");
            System.out.println("3. Change Password");
            System.out.println("4. Change Email");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (choice) {
                case 1:
                    getAllAvailableProjects();
                    break;
                case 2:
                    System.out.print("Enter Project ID to express interest: ");
                    String projectId = scanner.nextLine();
                    registerInterestToProject(projectId);
                    break;
                case 3:
                    System.out.print("Enter Password to change: ");
                    String password = scanner.nextLine();
                    changePassword(password);
                    break;
                case 4:
                    System.out.print("Enter Email to change: ");
                    String email = scanner.nextLine();
                    changeEmail(email);
                    break;
                case 5:
                    logout();
                    return;  // Exit the method, leading to logout or program termination
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
    
    public void getAllAvailableProjects() throws Exception {
        HttpResponse<String> response = HttpClientUtil.sendGetWithToken("http://example.com/api/projects?role=Student&available=true");
        if (response.statusCode() == 200) {
            JSONArray projects = new JSONArray(response.body());
            System.out.println("Available Projects:");
            for (int i = 0; i < projects.length(); i++) {
                JSONObject project = projects.getJSONObject(i);
                System.out.println("ID: " + project.getString("id") + ", Title: " + project.getString("title") + ", Description: " + project.getString("description"));
            }
        } else {
            System.out.println("Failed to get available projects.");
        }
    }

    public void registerInterestToProject(String projectId) throws Exception {
        HttpResponse<String> response = HttpClientUtil.sendPostWithToken("http://example.com/api/projects/" + projectId+"/interest", "");
        if (response.statusCode() == 200) {
            System.out.println("Registered interest successfully in project ID: " + projectId);
        } else {
            System.out.println("Failed to register interest in project ID: " + projectId);
        }
    }
}
