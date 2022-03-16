package com.nhxv.botbackend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class DiscordBot {
    private List<String> roles;
    private DiscordBotUser user;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @JsonProperty("user")
    public DiscordBotUser getDiscordBotUser() {
        return user;
    }

    @JsonProperty("user")
    public void setDiscordBotUser(DiscordBotUser discordBotUser) {
        this.user = discordBotUser;
    }

    @Override
    public String toString() {
        return "DiscordBot{" +
                "roles=" + roles +
                ", user=" + user +
                '}';
    }
}
