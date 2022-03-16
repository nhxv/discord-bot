package com.nhxv.botbackend.dto;

/**
 * @author Chinna
 * @since 26/3/18
 */
public enum SocialProvider {
	DISCORD("discord"),
	LOCAL("local");

	private String providerType;

	public String getProviderType() {
		return providerType;
	}

	SocialProvider(final String providerType) {
		this.providerType = providerType;
	}

}
