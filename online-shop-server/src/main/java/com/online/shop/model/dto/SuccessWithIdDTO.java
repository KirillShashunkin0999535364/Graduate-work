package com.online.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO to return when a request was successful.
 */
@Getter
@Setter
public class SuccessWithIdDTO {

    /**
     * HTTP status.
     */
    private int status;

    /**
     * Message.
     */
    private String message;

    /**
     * Object's id.
     */
    private Long id;
}
