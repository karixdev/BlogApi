package com.github.karixdev.blogapi.controller;

import com.github.karixdev.blogapi.dto.request.DemandPasswordResetRequest;
import com.github.karixdev.blogapi.dto.request.PasswordResetRequest;
import com.github.karixdev.blogapi.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/password-reset")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/send")
    public Map<String, String> sendEmailWithPasswordResetLink(
            @Valid @RequestBody DemandPasswordResetRequest passwordResetRequest
    ) {
        return passwordResetService.generateNewToken(passwordResetRequest);
    }

    @PostMapping("/{token}")
    public Map<String, String> changePassword(
            @PathVariable(name = "token") String token,
            @Valid @RequestBody PasswordResetRequest passwordResetRequest
    ) {
        return passwordResetService.changePassword(token, passwordResetRequest);
    }

}
