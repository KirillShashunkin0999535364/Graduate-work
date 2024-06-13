package com.online.shop.exceptions;


public class CouldntUploadPhotoException extends RuntimeException {

    public CouldntUploadPhotoException(final String message) {
        super(message);
    }
}
