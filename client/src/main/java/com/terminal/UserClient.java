package com.terminal;

import java.net.http.HttpResponse;
import org.json.JSONObject;

import com.terminal.util.HttpClientUtil;
import com.terminal.util.Token;
public class UserClient {

    public static void changePassword(String newPassword) throws Exception {
        JSONObject json = new JSONObject();
        json.put("Password", newPassword);

        // Use PATCH instead of POST for partial update
        HttpResponse<String> response = HttpClientUtil.sendPatchWithToken("http://example.com/api/users/password", json.toString());
        if (response.statusCode() == 200) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Failed to change password. Status code: " + response.statusCode());
        }
    }

    public static void changeEmail(String newEmail) throws Exception {
        JSONObject json = new JSONObject();
        json.put("Email", newEmail);

        // Use PATCH instead of POST for partial update
        HttpResponse<String> response = HttpClientUtil.sendPatchWithToken("http://example.com/api/users/email", json.toString());
        if (response.statusCode() == 200) {
            System.out.println("Email changed successfully.");
        } else {
            System.out.println("Failed to change email. Status code: " + response.statusCode());
        }
    }


    public static void logout() throws Exception {
        HttpResponse<String> response = HttpClientUtil.sendPostWithToken("http://example.com/api/logout", "");
        if (response.statusCode() == 200) {
            System.out.println("Logout successful.");
            Token.setAuthToken(null); // Clear the token
        } else {
            System.out.println("Logout failed.");
        }
    }
}