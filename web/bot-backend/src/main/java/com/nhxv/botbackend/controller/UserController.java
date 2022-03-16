package com.nhxv.botbackend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.nhxv.botbackend.config.CurrentUser;
import com.nhxv.botbackend.dto.*;
import com.nhxv.botbackend.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private OAuth2AuthorizedClientService clientService;

	@Value("${spring.security.oauth2.client.registration.discord.clientId}")
	private String clientId;

	@Value("${bot.token}")
	private String botToken;

	private final String url = "https://discord.com/api";

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<UserInfo> getCurrentUser(@CurrentUser LocalUser user) throws URISyntaxException, JsonProcessingException {
		OAuth2AuthorizedClient client = this.clientService.loadAuthorizedClient(
				user.getUser().getProvider(),
				user.getUser().getEmail()
		);
		String accessToken = client.getAccessToken().getTokenValue();
		RestTemplate restTemplate = new RestTemplate();
		final String myGuildUrl =  url + "/users/@me/guilds";
		URI myGuildUri = new URI(myGuildUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
		headers.set(HttpHeaders.USER_AGENT, "Discord app");
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<String> guildsRes = restTemplate.exchange(myGuildUri, HttpMethod.GET, request, String.class);
		List<DiscordGuild> managedDiscordGuilds = filterBot(filterPermission(processGuilds(guildsRes.getBody())));
		return ResponseEntity.ok(GeneralUtils.buildUserInfo(user, managedDiscordGuilds));
	}

	private List<DiscordGuild> processGuilds(String guildStr) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper.readValue(guildStr, new TypeReference<List<DiscordGuild>>(){});
	}

	private List<DiscordGuild> filterPermission(List<DiscordGuild> discordGuilds) {
		return discordGuilds.stream().filter(
				discordGuild -> discordGuild.isOwner() ||
						discordGuild.getPermissions().equals("ADMINISTRATOR") ||
						discordGuild.getPermissions().equals("MANAGE_GUILD")).collect(Collectors.toList());
	}

	/*
	** filter guild that has MichaelBot
	 */
	private List<DiscordGuild> filterBot(List<DiscordGuild> managedDiscordGuilds) throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "Bot " + botToken);
		headers.set(HttpHeaders.USER_AGENT, "Discord app");
		HttpEntity<String> request = new HttpEntity<>(headers);

		List<DiscordGuild> filteredDiscordGuilds = new ArrayList<>();

		for (DiscordGuild g : managedDiscordGuilds) {
			final String checkBotUrl = url + "/guilds/" + g.getId() + "/members/" + clientId;
			URI checkBotUri = new URI(checkBotUrl);
			try {
				ResponseEntity<String> botRes = restTemplate.exchange(checkBotUri, HttpMethod.GET, request, String.class);
				DiscordBot discordBot = processBot(botRes.getBody());
				System.out.println("Guild: " + g.getName() + " " + g.getId());
				DiscordGuild discordGuild = getChannels(g, restTemplate, request, discordBot.getRoles(), discordBot.getDiscordBotUser().getId());
				filteredDiscordGuilds.add(discordGuild);
			} catch(HttpStatusCodeException | JsonProcessingException e) {
				if (e instanceof HttpStatusCodeException) {
					System.out.println(((HttpStatusCodeException) e).getStatusCode());
				} else {
					e.printStackTrace();
				}
			}
		}
		return filteredDiscordGuilds;
	}

	private DiscordBot processBot(String botStr) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper.readValue(botStr, new TypeReference<DiscordBot>(){});
	}

	// get guild channels that MichaelBot can send message to
	private DiscordGuild getChannels(DiscordGuild g,
									 RestTemplate restTemplate,
									 HttpEntity<String> request,
									 List<String> botRoles,
									 String botId) throws JsonProcessingException, URISyntaxException {
		final String channelUrl = url + "/guilds/" + g.getId() + "/channels";
		URI channelUri = new URI(channelUrl);
		ResponseEntity<String> channelRes = restTemplate.exchange(channelUri, HttpMethod.GET, request, String.class);
		List<DiscordChannel> discordChannels = processChannels(channelRes.getBody());
		List<DiscordChannel> filteredDiscordChannels = discordChannels.stream().filter(
				discordChannel ->
						discordChannel.getType() == 0
						&& checkPermissionOverwrite(discordChannel.getPermissionOverwrites(), g.getId(), botRoles, botId)
				).collect(Collectors.toList());

		for (DiscordChannel channel : filteredDiscordChannels) {
			System.out.println(channel);
		}

		g.setChannels(filteredDiscordChannels); // set channel that bot can send message to
		return g;
	}

	private List<DiscordChannel> processChannels(String channelStr) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return objectMapper.readValue(channelStr, new TypeReference<List<DiscordChannel>>(){});
	}

	/*
	* filter allow and deny from overwrite
	*/
	private boolean checkPermissionOverwrite(List<DiscordOverwrite> discordOverwrites,
											 String everyoneId,
											 List<String> botRolesId,
											 String botId) {
		if (discordOverwrites == null || discordOverwrites.isEmpty()) {
			return true;
		}
		DiscordOverwrite botMemberOverwrite;
		List<DiscordOverwrite> botRoleOverwrites = new ArrayList<>();
		DiscordOverwrite everyoneOverwrite = null;
		for (DiscordOverwrite discordOverwrite : discordOverwrites) {
			if (discordOverwrite.getId().equals(botId)) {
				botMemberOverwrite = discordOverwrite;
				return canBotSendMessage(botMemberOverwrite.getDeny());
			} else if (botRolesId.contains(discordOverwrite.getId())) {
				botRoleOverwrites.add(discordOverwrite);
			} else if (discordOverwrite.getId().equals(everyoneId)) {
				everyoneOverwrite = discordOverwrite;
			}
		}
		if (!botRoleOverwrites.isEmpty()) {
			// false = untouched, true = enabled - like a switch
			boolean allowView = false;
			boolean denyView = false;
			boolean allowSend = false;
			boolean denySend = false;
			for (DiscordOverwrite botRoleOverwrite : botRoleOverwrites) {
				if (isAllowView(botRoleOverwrite.getAllow())) allowView = true;
				if (isDenyView(botRoleOverwrite.getDeny())) denyView = true;
				if (isAllowSend(botRoleOverwrite.getAllow())) allowSend = true;
				if (isDenySend(botRoleOverwrite.getDeny())) denySend = true;
			}
			boolean canView = false;
			boolean canSend = false;
			if (allowView) {
				canView = true;
			} else if (!denyView) { // when allow view and deny view are untouched -> can view
				canView = true;
			}
			if (allowSend) {
				canSend = true;
			} else if (!denySend) { // when allow send and deny send are untouched -> can send
				canSend = true;
			}
			if (canView) {
				return canSend;
			} else {
				return false;
			}
		} else if (everyoneOverwrite != null) {
			return canBotSendMessage(everyoneOverwrite.getDeny());
		}
		return true;
	}

	// for one overwrite
	private boolean canBotSendMessage(String deny) {
		return !isDenyView(deny) && !isDenySend(deny);
	}

	/*
	** for multiple overwrites at the same level
	 */
	private boolean isAllowView(String allow) {
		return (Long.parseLong(allow) & 0x400) == 0x400;
	}

	private boolean isDenyView(String deny) {
		return (Long.parseLong(deny) & 0x400) == 0x400;
	}

	private boolean isAllowSend(String allow) {
		return (Long.parseLong(allow) & 0x800) == 0x800;
	}

	private boolean isDenySend(String deny) {
		return (Long.parseLong(deny) & 0x800) == 0x800;
	}
}
