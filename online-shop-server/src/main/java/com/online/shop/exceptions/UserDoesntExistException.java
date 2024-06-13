package com.online.shop.exceptions;

public class UserDoesntExistException extends RuntimeException {

    public UserDoesntExistException(final String message) {
        super(message);
    }
}
