package com.online.shop.model.dto.auth;


import com.online.shop.security.SecurityConstants;
import lombok.Data;

/**
 * Authorization response DTO.
 */
@Data
public class AuthResponseDTO {

    /**
     * Access token.
     */
    private String accessToken;

    /**
     * Token's type.
     */
    private String tokenType = SecurityConstants.JWT_HEADER;

    /**
     * Constructor.
     *
     * @param accessToken is the access token.
     */
    public AuthResponseDTO(final String accessToken) {
        this.accessToken = accessToken;
    }
}
