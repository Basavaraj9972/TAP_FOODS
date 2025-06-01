package com.tap.com.cotroller;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import com.tap.com.service.GoogleTokenService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class GoogleAuthController {

    private final GoogleTokenService googleTokenService;

    public GoogleAuthController(GoogleTokenService googleTokenService) {
        this.googleTokenService = googleTokenService;
    }

    @PostMapping("/google")
    public ResponseEntity<Map<String, Object>> authenticateGoogleUser(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        System.out.println("Google token is : "+token);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Token is required"));
        }

        Map<String, Object> userInfo = googleTokenService.verifyGoogleToken(token);
        
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid token"));
        }

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAuthenticatedUser(Authentication authentication) {
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User user = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "User not authenticated"));
            }

            return ResponseEntity.ok(user.getAttributes());
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid authentication type"));
    }
}
