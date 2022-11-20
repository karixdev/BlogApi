package com.github.karixdev.blogapi.controller;

import com.github.karixdev.blogapi.dto.request.PasswordResetRequest;
import com.github.karixdev.blogapi.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/send")
    public Map<String, String> sendEmailWithPasswordResetLink(
            @Valid @RequestBody PasswordResetRequest passwordResetRequest
    ) {
        return passwordResetService.generateNewToken(passwordResetRequest);
    }

}
