package com.online.shop.exceptions;


public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException(final String message) {
        super(message);
    }
}
