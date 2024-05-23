package com.online.shop.exceptions;

/**
 * Exception thrown when the photo for user couldn't be upload.
 */
public class CouldntUploadPhotoException extends RuntimeException {

    /**
     * Constructor;
     */
    public CouldntUploadPhotoException(final String message) {
        super(message);
    }
}
