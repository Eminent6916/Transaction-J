package com.TransactionService.Config;

import com.TransactionService.Utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String GATEWAY_AUTH_HEADER = "X-User-Email";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        final String gatewayUserEmail = request.getHeader(GATEWAY_AUTH_HEADER);

        logger.info("=== JWT FILTER DEBUG ===");
        logger.info("Authorization Header: " + authorizationHeader);
        logger.info("Gateway Header (" + GATEWAY_AUTH_HEADER + "): " + gatewayUserEmail);
        logger.info("Request URI: " + request.getRequestURI());

        String username = null;
        String jwt = null;
        boolean authenticatedViaGateway = false;

        // 1. Determine the source of the username
        if (gatewayUserEmail != null && !gatewayUserEmail.trim().isEmpty()) {
            username = gatewayUserEmail.trim();
            authenticatedViaGateway = true;
            logger.info("Authenticating via Gateway header. Username: " + username+authorizationHeader+":::::;:"+SecurityContextHolder.getContext().getAuthentication());
        }
        else if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            logger.info("JWT Token extracted. Username: " + username);
            try {
                username = jwtUtil.extractUsername(jwt);
                logger.info("JWT Token extracted. Username::: " + username);
            } catch (Exception e) {
                logger.error("JWT Token extraction failed: " + e.getMessage(), e);
            }
        } else {
            logger.info("No authentication headers found");
        }

        // 2. Load UserDetails and set the Security Context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("Attempting to load user: " + username);

            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                logger.info("UserDetails loaded successfully. Class: " + userDetails.getClass().getName());
                logger.info("UserDetails username: " + userDetails.getUsername());
                logger.info("UserDetails authorities: " + userDetails.getAuthorities());

                boolean tokenIsValid = authenticatedViaGateway || (jwt != null && jwtUtil.validateToken(jwt, userDetails));

                if (tokenIsValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.info("Authentication SUCCESS for user: " + username);
                } else {
                    logger.warn("Token validation FAILED for user: " + username);
                }
            } catch (UsernameNotFoundException e) {
                logger.error("USER NOT FOUND in database: " + username, e);
                // Add error response if needed
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "User not found: " + username);
                return;
            } catch (Exception e) {
                logger.error("Error during authentication: " + e.getMessage(), e);
            }
        } else {
            logger.info("No username found or authentication already present");
        }

        // 3. Continue the filter chain
        filterChain.doFilter(request, response);
    }
}