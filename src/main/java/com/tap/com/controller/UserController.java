package com.tap.com.controller;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/google")
    public ResponseEntity<Map<String, Object>> getUser(@AuthenticationPrincipal OAuth2User user) {
        System.out.println("redirecting");
        
    	if (user == null) {
    		System.out.println("not present User");
            return new ResponseEntity<Map<String, Object>>(Map.of("error", "User not authenticated"),HttpStatus.OK);
        }else {
        	String userEmail = user.getAttribute("email");
	    	System.out.println("UserName"+user.getAttribute("name"));
	    	System.out.println("Email"+user.getAttribute("email"));
	    	System.out.println("Picture"+user.getAttribute("picture"));
        }
        return new ResponseEntity<Map<String, Object>>(Map.of(
            "name", user.getAttribute("name"),
            "email", user.getAttribute("email"),
            "picture", user.getAttribute("picture")
        ),HttpStatus.OK);
    }
}
