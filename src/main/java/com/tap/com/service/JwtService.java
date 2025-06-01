package com.tap.com.service;

import org.springframework.stereotype.Service;

import com.tap.com.JwtUtil;

@Service
public class JwtService {

    private final JwtUtil jwtUtil;

    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String generateJwtForUser(String username) {
        return jwtUtil.generateToken(username);
    }

    public boolean isValidToken(String token) {
        return jwtUtil.verifyToken(token) != null;
    }
}
