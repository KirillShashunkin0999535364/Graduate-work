package com.yashmerino.online.shop.model.dto.auth;


import lombok.Data;

import static com.yashmerino.online.shop.security.SecurityConstants.JWT_HEADER;

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
    private String tokenType = JWT_HEADER;

    /**
     * Constructor.
     *
     * @param accessToken is the access token.
     */
    public AuthResponseDTO(final String accessToken) {
        this.accessToken = accessToken;
    }
}
