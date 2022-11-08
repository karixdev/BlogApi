package com.github.karixdev.blogapi.auth;

import com.github.karixdev.blogapi.registration.RegistrationService;
import com.github.karixdev.blogapi.registration.dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public ResponseEntity<?> registerNewUser(
            @Valid @RequestBody RegistrationRequest registrationRequest
    ) {
        return new ResponseEntity<>(
                registrationService.registerNewUser(registrationRequest),
                HttpStatus.CREATED);
    }

}
