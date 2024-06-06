package com.online.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO to return when a request was successful.
 */
@Getter
@Setter
public class SuccessDTO {
    private int status;
    private String message;
}
