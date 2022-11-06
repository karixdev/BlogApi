package com.github.karixdev.blogapi.registration;

import com.github.karixdev.blogapi.registration.dto.RegistrationRequest;
import com.github.karixdev.blogapi.user.User;
import com.github.karixdev.blogapi.user.UserRepository;
import com.github.karixdev.blogapi.user.UserRole;
import com.github.karixdev.blogapi.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Map<String, Boolean> registerNewUser(RegistrationRequest registrationRequest) {

        String encodedPassword = passwordEncoder.encode(
                registrationRequest.getPlainPassword());

        User user = User.builder()
                .email(registrationRequest.getEmail())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .userRole(UserRole.USER)
                .isEnabled(Boolean.TRUE)
                .password(encodedPassword)
                .build();

        userRepository.save(user);

        return Map.of("success", Boolean.TRUE);
    }

}
