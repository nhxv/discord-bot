package com.nhxv.botbackend.dto;

import java.util.List;

public class DiscordChannel {
    private String id, name, permissions;
    private byte type;
    private List<DiscordOverwrite> permissionDiscordOverwrites;

    public DiscordChannel() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public List<DiscordOverwrite> getPermissionOverwrites() {
        return permissionDiscordOverwrites;
    }

    public void setPermissionOverwrites(List<DiscordOverwrite> permissionDiscordOverwrites) {
        this.permissionDiscordOverwrites = permissionDiscordOverwrites;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", permissions='" + permissions + '\'' +
                ", type=" + type +
                ", permissionOverwrites=" + permissionDiscordOverwrites +
                '}';
    }
}

