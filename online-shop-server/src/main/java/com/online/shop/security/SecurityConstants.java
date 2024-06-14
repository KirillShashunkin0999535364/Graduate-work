package com.online.shop.security;

public class SecurityConstants {

    public static final long JWT_EXPIRATION = 48 * 60 * 60 * 1000;

    public static final String JWT_HEADER = "Bearer ";

    public static final String AUTH_HEADER = "Authorization";

    private SecurityConstants() {

    }
}
