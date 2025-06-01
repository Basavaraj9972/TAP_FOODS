package com.tap.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class JwtServiceChecking {

    @Autowired
    private JwtService jwtService;

    @PostConstruct
    public void init() {
//        checkJwtFunctionality(); // ✅ Runs when app starts
    }
    public void checkJwtFunctionality() {
        // Generate Token
        String token = jwtService.generateJwtForUser("Basavaraj");
        System.out.println("Generated Token: " + token);

        // Verify Token
        boolean isValid = jwtService.isValidToken(token);
        System.out.println(isValid ? "Token is valid!" : "Token is invalid or expired!");
    }
}
