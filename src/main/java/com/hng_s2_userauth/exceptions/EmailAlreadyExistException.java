package com.hng_s2_userauth.exceptions;

public class EmailAlreadyExistException extends RuntimeException {

    public EmailAlreadyExistException(String message){
        super(message);
    }
}
