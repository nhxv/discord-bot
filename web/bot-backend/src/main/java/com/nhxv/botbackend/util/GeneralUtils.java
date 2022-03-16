package com.nhxv.botbackend.util;

import com.nhxv.botbackend.dto.DiscordGuild;
import com.nhxv.botbackend.dto.LocalUser;
import com.nhxv.botbackend.dto.SocialProvider;
import com.nhxv.botbackend.dto.UserInfo;
import com.nhxv.botbackend.model.Role;
import com.nhxv.botbackend.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Chinna
 *
 */
public class GeneralUtils {

	public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	public static SocialProvider toSocialProvider(String providerId) {
		for (SocialProvider socialProvider : SocialProvider.values()) {
			if (socialProvider.getProviderType().equals(providerId)) {
				return socialProvider;
			}
		}
		return SocialProvider.LOCAL;
	}

	public static UserInfo buildUserInfo(LocalUser localUser, List<DiscordGuild> discordGuilds) {
		List<String> roles = localUser.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());
		User user = localUser.getUser();
		return new UserInfo(user.getId().toString(),
				user.getDisplayName(),
				user.getEmail(),
				user.getAvatar(),
				user.getProviderUserId(),
				roles,
                discordGuilds);
	}
}
