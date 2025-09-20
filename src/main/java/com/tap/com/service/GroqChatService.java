package com.tap.com.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GroqChatService {

    private final String GROQ_API_KEY = "YOUR_API_KEY"; // replace with your key
    private final String GROQ_URL = "https://api.groq.com/v1/engines/gpt-oss-20b/completions"; // correct endpoint

    public String chat(String message) {
        RestTemplate restTemplate = new RestTemplate();

        // Create JSON body
        String requestJson = "{ \"model\": \"openai/gpt-oss-20b\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(GROQ_API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                GROQ_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }
}
