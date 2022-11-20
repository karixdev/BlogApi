package com.github.karixdev.blogapi.exception;

public class PasswordResetTokenExpiredException extends RuntimeException {
    public PasswordResetTokenExpiredException(String message) {
        super(message);
    }
}
