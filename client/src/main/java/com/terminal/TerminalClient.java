package com.terminal;

import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONObject;

import com.terminal.util.HttpClientUtil;
import com.terminal.util.Token;

public class TerminalClient {
    private static final Scanner scanner = new Scanner(System.in);
    private static Long userId;

    public static void main(String[] args) throws Exception {
        System.out.println("1. Register");
        System.out.println("2. Login");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    public static void register() throws Exception {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter role (STAFF or STUDENT):");
        String role = scanner.nextLine();
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
    
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        json.put("role", role);
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("email", email);
        HttpResponse<String> response = HttpClientUtil.sendPost("http://localhost:8080/api/users", json.toString());
        if (response.statusCode() == 200) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration failed. Status code: " + response.statusCode() + " Response: " + response.body());
        }
    }

   private static void login() throws Exception {
    System.out.println("Enter username:");
    String username = scanner.nextLine();
    System.out.println("Enter password:");
    String password = scanner.nextLine();

    JSONObject json = new JSONObject();
    json.put("username", username);
    json.put("password", password);

    HttpResponse<String> response = HttpClientUtil.sendPost("http://localhost:8080/api/auth/login", json.toString());

    if (response.statusCode() == 200) {
        JSONObject responseBody = new JSONObject(response.body());
        boolean result = responseBody.getBoolean("result");
        if (result) {
            String token = responseBody.getString("token");
            userId = responseBody.getLong("userId");
            Token.setAuthToken(token); 
            String role = responseBody.getJSONObject("user").getString("role");
            switch (role.toLowerCase()) {
                case "staff":
                    StaffClient staffClient = new StaffClient();
                    staffClient.showMenu(userId);
                    break;
                case "student":
                    StudentClient studentClient = new StudentClient();
                    studentClient.showMenu(userId);
                    break;
                default:
                    System.out.println("Invalid role.");
                    break;
            }
        } else {
            String message = responseBody.getString("message");
            System.out.println("Login failed: " + message);
        }
    } else {
        System.out.println("Login failed. Status code: " + response.statusCode());
    }
}

}