package com.hng_s2_userauth.exceptions;

public class InvalidCredentialException extends  RuntimeException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}
