package com.yashmerino.online.shop.security;

/**
 * Constant values for security configuration.
 */
public class SecurityConstants {

    /**
     * Time after which JWT token expires.
     */
    public static final long JWT_EXPIRATION = 700000;

    /**
     * Bearer part from the auth header.
     */
    public static final String JWT_HEADER = "Bearer ";

    /**
     * Auth header.
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * Private constructor to not allow instantiation.
     */
    private SecurityConstants() {

    }
}
