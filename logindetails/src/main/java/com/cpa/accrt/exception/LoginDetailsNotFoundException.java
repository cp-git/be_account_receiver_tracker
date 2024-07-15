package com.cpa.accrt.exception;

public class LoginDetailsNotFoundException extends RuntimeException {

    public LoginDetailsNotFoundException(String message) {
        super(message);
    }
}