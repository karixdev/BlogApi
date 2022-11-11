package com.github.karixdev.blogapi.service;

import com.github.karixdev.blogapi.dto.RegistrationRequest;
import com.github.karixdev.blogapi.entity.ConfirmationToken;
import com.github.karixdev.blogapi.entity.User;
import com.github.karixdev.blogapi.repository.UserRepository;
import com.github.karixdev.blogapi.entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ConfirmationTokenService tokenService;
    private final EmailService emailService;

    public Map<String, Boolean> registerNewUser(RegistrationRequest registrationRequest) {

        String encodedPassword = passwordEncoder.encode(
                registrationRequest.getPlainPassword());

        User user = User.builder()
                .email(registrationRequest.getEmail())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .userRole(UserRole.USER)
                .isEnabled(Boolean.FALSE)
                .password(encodedPassword)
                .build();

        ConfirmationToken token = tokenService.createTokenForUser(user);

        userRepository.save(user);

        emailService.sendEmailConfirmation(token);

        return Map.of("success", Boolean.TRUE);
    }

}
