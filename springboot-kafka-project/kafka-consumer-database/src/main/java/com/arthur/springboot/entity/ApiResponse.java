package com.arthur.springboot.entity;

public class ApiResponse {
    private String type;
    private boolean isBot;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "type='" + type + '\'' +
                ", isBot=" + isBot +
                '}';
    }
}
