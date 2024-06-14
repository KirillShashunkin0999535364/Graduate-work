package com.online.shop.exceptions;


import java.util.List;

public class FieldErrorResponse {

    private List<CustomFieldError> fieldErrors;

    public List<CustomFieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<CustomFieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
