package com.github.karixdev.blogapi.exception;

public class PasswordResetTokenAlreadySendException extends RuntimeException {
    public PasswordResetTokenAlreadySendException(String message) {
        super(message);
    }
}
