package com.yashmerino.online.shop.exceptions;


import java.util.List;

/**
 * Field error response class.
 */
public class FieldErrorResponse {

    /**
     * The list with validated fields and error messages.
     */
    private List<CustomFieldError> fieldErrors;

    /**
     * @return the validated fields and errors.
     */
    public List<CustomFieldError> getFieldErrors() {
        return fieldErrors;
    }

    /**
     * Sets the valideted fields and their errors.
     *
     * @param fieldErrors is the list with validated fields and their error messages.
     */
    public void setFieldErrors(List<CustomFieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
