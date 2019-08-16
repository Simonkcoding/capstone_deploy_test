package com.company.SimonKwokU1Capstone.exception;

public class RequestNotFoundException extends RuntimeException{
    public RequestNotFoundException() {
    }

    public RequestNotFoundException(String message) {
        super(message);
    }
}
