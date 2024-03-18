package com.project.allocation.service.impl;

import com.project.allocation.dto.AuthResponseDTO;
import com.project.allocation.model.User;
import com.project.allocation.repository.UserRepository;
import com.project.allocation.service.AuthService;
import com.project.allocation.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    public AuthServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponseDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return AuthResponseDTO.fail("Invalid username or password");
        }

        if (!user.getPassword().equals(password)) {
            return AuthResponseDTO.fail("Invalid username or password");
        }

        return AuthResponseDTO.success(jwtUtil.createToken(user));
    }

    @Override
    public boolean logout() {
        return false;
    }
}
