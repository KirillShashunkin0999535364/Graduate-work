package com.online.shop.security;


import com.online.shop.utils.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.online.shop.security.SecurityConstants.JWT_EXPIRATION;
import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
@SuppressWarnings("java:S6437")
public class JwtProvider {

    private ApplicationProperties applicationProperties;


    public JwtProvider(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiringDate = new Date(currentDate.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expiringDate)
                .signWith(getSigningKey(), HS512)
                .compact();
    }


    public String getUsernameFromJWT(final String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }


    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("JWT Token is not valid, it could be because it's expired or incorrect.");
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(applicationProperties.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
