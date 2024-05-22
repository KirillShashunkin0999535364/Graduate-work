package com.yashmerino.online.shop.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Custom error response returned to user when an error occurs.
 */
@Getter
@Setter
public class CustomErrorResponse {

    /**
     * Timestamp.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Error's HTTP status.
     */
    private int status;

    /**
     * Error message.
     */
    private String error;
}
