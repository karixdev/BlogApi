package com.github.karixdev.blogapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "jwt")
public record JwtConfiguration(
        Integer expirationHours,
        RSAPrivateKey privateKey,
        RSAPublicKey publicKey
) {}
