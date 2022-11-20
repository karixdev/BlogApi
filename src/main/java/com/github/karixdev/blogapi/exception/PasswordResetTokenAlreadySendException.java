package com.github.karixdev.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordResetTokenAlreadySendException extends RuntimeException {
    public PasswordResetTokenAlreadySendException(String message) {
        super(message);
    }
}
