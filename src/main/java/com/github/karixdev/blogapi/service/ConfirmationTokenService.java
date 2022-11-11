package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.entity.ConfirmationToken;
import com.github.karixdev.blogapi.repository.ConfirmationTokenRepository;
import com.github.karixdev.blogapi.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private static final long HOURS_UNTIL_EXPIRATION = 1;

    private final ConfirmationTokenRepository tokenRepository;
    private final UserService userService;

    public ConfirmationToken createTokenForUser(User user) {
        String uuid = UUID.randomUUID().toString();

        return tokenRepository.save(
                ConfirmationToken.builder()
                        .token(uuid)
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .expiresAt(LocalDateTime.now().plusHours(HOURS_UNTIL_EXPIRATION))
                        .build());
    }

    public Map<String, String> confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenRepository
                .findByToken(token)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Token not found");
                });

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already confirmed");
        }

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        confirmationToken.setConfirmedAt(LocalDateTime.now());
        userService.enableUser(confirmationToken.getUser());

        tokenRepository.save(confirmationToken);

        return Map.of("message", "confirmed");
    }

}
