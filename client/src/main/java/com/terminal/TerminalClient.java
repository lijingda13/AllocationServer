package com.terminal;

import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONObject;

import com.terminal.util.HttpClientUtil;
import com.terminal.util.Token;

public class TerminalClient {
    private static final Scanner scanner = new Scanner(System.in);

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

    private static void register() throws Exception {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        json.put("lastName", email);

        HttpResponse<String> response = HttpClientUtil.sendPost("http://example.com/api/register", json.toString());

        if (response.statusCode() == 201) {
            System.out.println("Registration successful.");
        } else {
            System.out.println("Registration failed.");
        }
    }

    private static void login() throws Exception {
        System.out.println("Enter role (student/staff):");
        String role = scanner.nextLine();
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        JSONObject json = new JSONObject();
        json.put("role", role);
        json.put("username", username);
        json.put("password", password);

        HttpResponse<String> response = HttpClientUtil.sendPost("http://example.com/api/login", json.toString());
        if (response.statusCode() == 200) {
            JSONObject responseBody = new JSONObject(response.body());
            String token = responseBody.getString("token");
            Token.setAuthToken(token); // Store the token
            switch (role.toLowerCase()) {
                case "staff":
                   StaffClient staffClient = new StaffClient();
                    staffClient.showMenu();
                    break;
                case "student":
                    StudentClient studentClient = new StudentClient();
                    studentClient.showMenu();
                    break;
                default:
                    System.out.println("Invalid role.");
                    break;
            }
        } else {
            System.out.println("Login failed.");
        }
    }

    
    //get projects -pid/title/description
    //Propose project -title/description
    //assign project - pid/uid
    //check interest list - pid -uid/username

    //projectlist -pid/title/description/teachername/register_status
    //register interest -pid/uid

    //logout -token
}