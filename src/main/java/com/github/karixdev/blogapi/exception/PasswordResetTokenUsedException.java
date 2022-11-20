package com.github.karixdev.blogapi.exception;

public class PasswordResetTokenUsedException extends RuntimeException {
    public PasswordResetTokenUsedException(String message) {
        super(message);
    }
}
