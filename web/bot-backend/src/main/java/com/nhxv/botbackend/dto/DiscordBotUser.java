package com.nhxv.botbackend.dto;

public class DiscordBotUser {
    private String id, username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "DiscordBotUser{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
