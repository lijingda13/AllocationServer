package com.project.allocation.service;

public interface AuthService {
    boolean login(String username, String password);

    boolean logout();
}
