package com.TransactionService.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Custom filter to authenticate requests coming from trusted internal microservices
 * using a shared secret key (Bearer token).
 * This filter runs BEFORE the standard JWT filter.
 */
@Component
public class InternalServiceAuthFilter extends OncePerRequestFilter {

    // Must match the key used in the FeignClientInterceptor in the Transaction Service
    @Value("${service.internal-auth-token}")
    private String internalAuthToken;

    private static final String SERVICE_PRINCIPAL = "InternalService";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 1. Check if the request carries the internal token
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);

            // 2. Validate the token against the expected secret
            if (token.equals(internalAuthToken)) {

                // 3. Create and set the internal service authentication
                InternalServiceAuthenticationToken authToken =
                        new InternalServiceAuthenticationToken(SERVICE_PRINCIPAL);

                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.debug("Successfully authenticated as internal service.");

                // If authenticated as a service, proceed immediately and skip further security filters
                filterChain.doFilter(request, response);
                return;
            }
        }

        // If not an internal service or token didn't match, proceed to the next filter (e.g., JwtAuthenticationFilter)
        filterChain.doFilter(request, response);
    }
}
