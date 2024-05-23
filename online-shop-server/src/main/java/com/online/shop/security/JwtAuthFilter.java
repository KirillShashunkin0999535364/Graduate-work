package com.online.shop.security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.online.shop.security.SecurityConstants.AUTH_HEADER;
import static com.online.shop.security.SecurityConstants.JWT_HEADER;

/**
 * JWT Auth Filter.
 */
public class JwtAuthFilter extends OncePerRequestFilter {

    /**
     * JWT Token generator.
     */
    private final JwtProvider tokenGenerator;

    /**
     * Custom user details service.
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Constructor.
     *
     * @param tokenGenerator           is the token generator.
     * @param customUserDetailsService is the service that deals with user's details.
     */
    public JwtAuthFilter(JwtProvider tokenGenerator, CustomUserDetailsService customUserDetailsService) {
        this.tokenGenerator = tokenGenerator;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.getJWTFromRequest(request);

        if (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) {
            String username = tokenGenerator.getUsernameFromJWT(token);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts JWT token from request.
     *
     * @param request is the client's request.
     * @return JWT Token.
     */
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTH_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JWT_HEADER)) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
