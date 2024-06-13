package com.online.shop.exceptions;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(final String message) {
        super(message);
    }
}
