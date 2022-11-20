package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.dto.request.DemandPasswordResetRequest;
import com.github.karixdev.blogapi.dto.request.PasswordResetRequest;
import com.github.karixdev.blogapi.entity.PasswordResetToken;
import com.github.karixdev.blogapi.entity.User;
import com.github.karixdev.blogapi.exception.PasswordResetTokenAlreadySendException;
import com.github.karixdev.blogapi.exception.PasswordResetTokenExpiredException;
import com.github.karixdev.blogapi.exception.PasswordResetTokenUsedException;
import com.github.karixdev.blogapi.exception.ResourceNotFoundException;
import com.github.karixdev.blogapi.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PasswordResetService {

    private final long TOKEN_EXPIRATION_MINUTES = 15;

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder encoder;

    public Map<String, String> generateNewToken(DemandPasswordResetRequest passwordResetRequest) {
        User user;

        try {
            user = userService.findByEmailOrThrowException(
                    passwordResetRequest.getEmail());
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return Map.of("message", "If user exists then email has been sent");
        }

        if (!canUserGenerateNewToken(user)) {
            throw new PasswordResetTokenAlreadySendException(
                    "Email with password reset link has already been sent");
        }

        PasswordResetToken token = createToken(user);

        emailService.send(token.getUser(), "Password reset", getEmailTemplate(token));

        return Map.of("message", "If user exists then email has been sent");
    }

    public boolean canUserGenerateNewToken(User user) {
        Optional<PasswordResetToken> optionalToken =
                passwordResetTokenRepository.findByUser(user);

        if (optionalToken.isEmpty()) {
            return true;
        }

        PasswordResetToken token = optionalToken.get();
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(token.getExpiresAt()) && token.getResetAt() != null) {
            return true;
        }

        return now.isAfter(token.getExpiresAt());
    }

    public PasswordResetToken createToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        String uuid = UUID.randomUUID().toString();

        return passwordResetTokenRepository.save(PasswordResetToken.builder()
                .token(uuid)
                .user(user)
                .createdAt(now)
                .expiresAt(now.plusMinutes(TOKEN_EXPIRATION_MINUTES))
                .build());
    }

    public Map<String, String> changePassword(String token, PasswordResetRequest passwordResetRequest) {
        PasswordResetToken passwordResetToken =
                passwordResetTokenRepository.findByToken(token)
                        .orElseThrow(() -> {
                            throw new ResourceNotFoundException("Password reset token not found");
                        });

        LocalDateTime now = LocalDateTime.now();

        if (passwordResetToken.getResetAt() != null) {
            throw new PasswordResetTokenUsedException("Token has been already used");
        }

        if (passwordResetToken.getResetAt() == null &&
                now.isAfter(passwordResetToken.getExpiresAt())) {
            throw new PasswordResetTokenExpiredException("Token has expired");
        }

        User user = passwordResetToken.getUser();
        userService.updatePassword(user, passwordResetRequest.getPassword());

        passwordResetToken.setResetAt(LocalDateTime.now());
        passwordResetTokenRepository.save(passwordResetToken);

        return Map.of("message", "success");
    }

    private String getEmailTemplate(PasswordResetToken token) {
        String template = """
                <div>
                <p>Hello %s</p>
                <p>Here's a <a href="http://localhost:8080/api/password-rest/%s">link</a> to reset your password - be quick because it only lasts for %d minutes</p>
                </div>
                """;

        return String.format(template, token.getUser().getFirstName(), token.getToken(), TOKEN_EXPIRATION_MINUTES);
    }
}
