package com.example.sae.exceptions;

public class UserNotEcurieException extends RuntimeException {
    public UserNotEcurieException() {
        super("You are not an ecurie account");
    }

    public UserNotEcurieException(String message) {
        super(message);
    }
}
