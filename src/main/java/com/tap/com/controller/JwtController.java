package com.tap.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tap.com.JwtUtil;
import com.tap.com.model.TokenObject;
import com.tap.com.model.UserEntity;
import com.tap.com.service.JwtServiceChecking;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping("/jwt")
public class JwtController {

    private final JwtUtil jwtUtil;
    private final JwtServiceChecking jwtServiceChecking;

    @Autowired
    public JwtController(JwtUtil jwtUtil, JwtServiceChecking jwtServiceChecking) {
        this.jwtUtil = jwtUtil;
        this.jwtServiceChecking = jwtServiceChecking;
    }

    @GetMapping(value = "/generate/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenObject> generateToken(@PathVariable String username) {
    	System.out.println(username);
        String token = jwtUtil.generateToken(username);
        TokenObject tokenObject = new TokenObject();
        tokenObject.setUserName(username);
        tokenObject.setToken(token);
        return new ResponseEntity<TokenObject>(tokenObject,HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(@RequestParam String token) {
        Claims claims = jwtUtil.verifyToken(token);
        return (claims != null) ? new ResponseEntity<String>("Valid Token for " + claims.getSubject()+" " +claims.getExpiration()+" "+ claims.getIssuedAt() ,HttpStatus.OK): new ResponseEntity<String>("Invalid or Expired Token!",HttpStatus.OK);
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkJwt() {
        jwtServiceChecking.checkJwtFunctionality();
        return new ResponseEntity<String>( "JWT Check Executed! See logs for details.",HttpStatus.OK);
    }
}
