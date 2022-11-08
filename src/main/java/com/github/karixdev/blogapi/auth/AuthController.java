package com.github.karixdev.blogapi.auth;

import com.github.karixdev.blogapi.registration.RegistrationService;
import com.github.karixdev.blogapi.registration.dto.RegistrationRequest;
import com.github.karixdev.blogapi.registration.token.ConfirmationTokenService;
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

}
