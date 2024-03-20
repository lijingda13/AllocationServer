package com.terminal.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;


public class HttpClientUtil {
    private static final HttpClient client = HttpClient.newHttpClient();

    public static HttpResponse<String> sendPost(String uri, String data) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public static HttpResponse<String> sendPatchWithToken(String uri, String data) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token.getAuthToken()) // Use the stored token for authentication
                .method("PATCH", BodyPublishers.ofString(data)) // Using the PATCH method
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    // Method to send requests with an Authorization header
    public static HttpResponse<String> sendPostWithToken(String uri, String data) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + Token.getAuthToken()) // Use the token here
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static HttpResponse<String> sendGetWithToken(String uri) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .header("Authorization", "Bearer " + Token.getAuthToken()) // Include the token in the Authorization header
                .GET() // This is a GET request
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Similar methods for GET, PUT, DELETE, etc. that require token can be added
}