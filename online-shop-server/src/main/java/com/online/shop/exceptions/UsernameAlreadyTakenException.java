package com.online.shop.exceptions;

/**
 * Exception thrown when the username field is already taken.
 */
public class UsernameAlreadyTakenException extends RuntimeException {

    /**
     * Constructor;
     */
    public UsernameAlreadyTakenException(final String message) {
        super(message);
    }
}
