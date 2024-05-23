package com.online.shop.exceptions;
/**
 * Exception thrown when the user doesn't exist.
 */
public class UserDoesntExistException extends RuntimeException {

    /**
     * Constructor;
     */
    public UserDoesntExistException(final String message) {
        super(message);
    }
}
