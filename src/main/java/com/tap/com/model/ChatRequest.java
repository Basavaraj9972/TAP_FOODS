package com.tap.com.model;

public class ChatRequest {
    private String message;
    private String model = "gpt-4"; // default model

    // Getters and setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
}
