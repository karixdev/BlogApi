package com.github.karixdev.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordResetTokenUsedException extends RuntimeException {
    public PasswordResetTokenUsedException(String message) {
        super(message);
    }
}
