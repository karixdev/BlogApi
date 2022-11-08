package com.github.karixdev.blogapi.registration.token;

import com.github.karixdev.blogapi.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private static final long HOURS_UNTIL_EXPIRATION = 1;

    private final ConfirmationTokenRepository tokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        tokenRepository.save(confirmationToken);
    }

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

}
