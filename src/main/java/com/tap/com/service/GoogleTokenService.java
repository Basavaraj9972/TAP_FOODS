package com.tap.com.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GoogleTokenService {
    
    private static final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo?access_token=";

    public Map<String, Object> verifyGoogleToken(String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(GOOGLE_USER_INFO_URL + token, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response);

            Map<String, Object> userDetails = new HashMap<>();
            userDetails.put("email", jsonNode.has("email") ? jsonNode.get("email").asText() : "Unknown");
            userDetails.put("name", jsonNode.has("name") ? jsonNode.get("name").asText() : "Unknown");
            userDetails.put("picture", jsonNode.has("picture") ? jsonNode.get("picture").asText() : "");

            return userDetails;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Map.of("error", "Invalid or expired token");
    }
}
