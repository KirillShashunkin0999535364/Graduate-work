package com.yashmerino.online.shop.exceptions;

/**
 * Exception thrown when the email field is already taken.
 */
public class EmailAlreadyTakenException extends RuntimeException {

    /**
     * Constructor;
     */
    public EmailAlreadyTakenException(final String message) {
        super(message);
    }
}
