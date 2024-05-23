package com.online.shop.exceptions;

/**
 * Custom error class for field validation.
 */
public class CustomFieldError {

    /**
     * The validated field.
     */
    private String field;

    /**
     * The error message.
     */
    private String message;

    /**
     * @return the validated field.
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the validated field.
     *
     * @param field is the validated field.
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message is the error message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
