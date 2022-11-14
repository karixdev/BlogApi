package com.github.karixdev.blogapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtConfiguration(String signingKey, Integer expirationHours) {
}
