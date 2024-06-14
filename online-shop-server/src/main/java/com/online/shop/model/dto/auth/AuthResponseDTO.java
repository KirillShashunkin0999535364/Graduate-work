package com.online.shop.model.dto.auth;


import com.online.shop.security.SecurityConstants;
import lombok.Data;


@Data
public class AuthResponseDTO {

    private String accessToken;

    private String tokenType = SecurityConstants.JWT_HEADER;

    public AuthResponseDTO(final String accessToken) {
        this.accessToken = accessToken;
    }
}
