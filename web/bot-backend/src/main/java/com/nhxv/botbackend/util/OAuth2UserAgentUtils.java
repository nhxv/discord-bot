package com.nhxv.botbackend.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;

/**
*** providers host on cloudflare requires User-Agent header
 */
public class OAuth2UserAgentUtils {
    public static RequestEntity<?> withUserAgent(RequestEntity<?> request) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(request.getHeaders());
        headers.add(HttpHeaders.USER_AGENT, DISCORD_USER_AGENT);

        return new RequestEntity<>(request.getBody(), headers, request.getMethod(), request.getUrl());
    }

    private static final String DISCORD_USER_AGENT = "Discord app";
}
