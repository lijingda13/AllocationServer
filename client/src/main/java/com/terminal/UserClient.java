package com.terminal;

import java.net.http.HttpResponse;
import org.json.JSONObject;

import com.terminal.util.HttpClientUtil;
import com.terminal.util.Token;
public class UserClient {
    protected static Long id;
    public void showMenu(Long id) throws Exception{
    UserClient.id = id;
    };
    public static void changePassword(String newPassword) throws Exception {
        JSONObject json = new JSONObject();
        json.put("password", newPassword); // 将密码字段设为小写

        HttpResponse<String> response = HttpClientUtil.sendPatchWithToken("http://localhost:8080/api/users/" + id, json.toString()); // 使用PATCH请求
        if (response.statusCode() == 200) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Failed to change password. Status code: " + response.statusCode());
        }
    }

    // 更新用户邮箱
    public static void changeEmail(String newEmail) throws Exception {
        JSONObject json = new JSONObject();
        json.put("email", newEmail); // 将电子邮件字段设为小写

        HttpResponse<String> response = HttpClientUtil.sendPatchWithToken("http://localhost:8080/api/users/" + id, json.toString()); // 使用PATCH请求
        if (response.statusCode() == 200) {
            System.out.println("Email changed successfully.");
        } else {
            System.out.println("Failed to change email. Status code: " + response.statusCode());
        }
    }

    public void getUserInformation() {
        String requestUri = "http://localhost:8080/api/users/" + id;

        try {
            HttpResponse<String> response = HttpClientUtil.sendGetWithToken(requestUri);
            switch (response.statusCode()) {
                case 200:
                    // 请求成功，打印用户信息
                    System.out.println("User information retrieved successfully:");
                    JSONObject userJson = new JSONObject(response.body());
                    System.out.println("ID: " + userJson.getInt("id"));
                    System.out.println("Username: " + userJson.getString("username"));
                    System.out.println("Role: " + userJson.getString("role"));
                    System.out.println("First Name: " + userJson.getString("firstName"));
                    System.out.println("Last Name: " + userJson.getString("lastName"));
                    System.out.println("Email: " + userJson.getString("email"));
                    break;
                case 403:
                    System.out.println("User is not authorized to access the resource.");
                    break;
                case 404:
                    System.out.println("User not found.");
                    break;
                default:
                    System.out.println("An error occurred. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}