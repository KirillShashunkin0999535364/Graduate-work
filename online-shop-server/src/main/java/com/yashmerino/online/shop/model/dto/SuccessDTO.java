package com.yashmerino.online.shop.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO to return when a request was successful.
 */
@Getter
@Setter
public class SuccessDTO {

    /**
     * HTTP status.
     */
    private int status;

    /**
     * Message.
     */
    private String message;
}
