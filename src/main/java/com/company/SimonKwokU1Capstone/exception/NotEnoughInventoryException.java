package com.company.SimonKwokU1Capstone.exception;

public class NotEnoughInventoryException extends RuntimeException {

    public NotEnoughInventoryException() {

    }

    public NotEnoughInventoryException(String message) {
        super(message);
    }
}