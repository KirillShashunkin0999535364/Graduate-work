package com.yashmerino.online.shop.exceptions;

/**
 * Exception thrown when the request is bad.
 */
public class InvalidInputException extends RuntimeException {

    /**
     * Constructor;
     */
    public InvalidInputException(final String message) {
        super(message);
    }
}
