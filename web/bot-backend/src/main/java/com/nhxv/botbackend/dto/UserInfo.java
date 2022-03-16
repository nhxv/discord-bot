package com.nhxv.botbackend.dto;

import java.util.List;

public class UserInfo {
	private String id, displayName, email, avatar, providerUserId;
	private List<String> roles;
	private List<DiscordGuild> discordGuilds;

	public UserInfo(String id,
					String displayName,
					String email,
					String avatar,
					String providerUserId,
					List<String> roles,
					List<DiscordGuild> discordGuilds) {
		this.id = id;
		this.displayName = displayName;
		this.email = email;
		this.avatar = avatar;
		this.providerUserId = providerUserId;
		this.roles = roles;
		this.discordGuilds = discordGuilds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<DiscordGuild> getGuilds() {
		return discordGuilds;
	}

	public void setGuilds(List<DiscordGuild> discordGuilds) {
		this.discordGuilds = discordGuilds;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				"id='" + id + '\'' +
				", displayName='" + displayName + '\'' +
				", email='" + email + '\'' +
				", roles=" + roles +
				", avatar=" + avatar +
				", providerUserId=" + providerUserId +
				", guilds=" + discordGuilds +
				'}';
	}
}
