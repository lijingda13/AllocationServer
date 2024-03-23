package com.project.allocation.service;

import com.project.allocation.dto.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(String username, String password);
}
