package com.github.karixdev.blogapi.controller;

import com.github.karixdev.blogapi.config.JwtConfiguration;
import com.github.karixdev.blogapi.dto.request.LoginRequest;
import com.github.karixdev.blogapi.dto.response.LoginResponse;
import com.github.karixdev.blogapi.service.LoginService;
import com.github.karixdev.blogapi.service.RegistrationService;
import com.github.karixdev.blogapi.dto.request.RegistrationRequest;
import com.github.karixdev.blogapi.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;
    private final ConfirmationTokenService tokenService;
    private final JwtConfiguration jwtConfiguration;
    private final LoginService loginService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerNewUser(
            @Valid @RequestBody RegistrationRequest registrationRequest
    ) {
        return new ResponseEntity<>(
                registrationService.registerNewUser(registrationRequest),
                HttpStatus.CREATED);
    }

    @PostMapping("/confirm")
    public Map<String, String> confirmEmail(
            @RequestParam(name = "token") String token
    ) {
        return tokenService.confirmToken(token);
    }

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        return loginService.login(loginRequest);
    }

}
