package com.nhxv.botbackend.dto;

public class DiscordOverwrite {
    private String id, allow, deny, type;

    public DiscordOverwrite() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAllow() {
        return allow;
    }

    public void setAllow(String allow) {
        this.allow = allow;
    }

    public String getDeny() {
        return deny;
    }

    public void setDeny(String deny) {
        this.deny = deny;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Overwrite{" +
                "id='" + id + '\'' +
                ", allow='" + allow + '\'' +
                ", deny='" + deny + '\'' +
                ", type=" + type +
                '}';
    }
}
