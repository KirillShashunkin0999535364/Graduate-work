package com.online.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO to return when a request was successful.
 */
@Getter
@Setter
public class SuccessWithIdDTO {
    private int status;
    private String message;
    private Long id;
}
