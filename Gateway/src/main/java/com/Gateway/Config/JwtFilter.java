package com.Gateway.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String SECRET;

    // Corrected whitelist - use consistent path patterns
    private static final String[] WHITELIST = {
            "/users/auth/sendVerification",
            "/users/auth/login",
            "/users/auth/otpVerification",
            "/users/createPassword",
            "/users/auth/**"  // This should catch all auth endpoints
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        System.out.println("JWT Filter - Path: " + path + ", Method: " + method);

        // Skip JWT check for whitelisted paths
        if (isWhitelisted(path)) {
            System.out.println("Whitelisted path - skipping JWT validation: " + path);
            return chain.filter(exchange);
        }

        System.out.println("Protected path - validating JWT: " + path);

        // JWT validation for all other requests
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(SECRET.getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.getSubject();
                String id = String.valueOf(claims.get("id"));

                exchange = exchange.mutate()
                        .request(r -> r.headers(headers -> {
                            headers.add("X-User-Id", id);
                            headers.add("X-User-Email", email);
                            headers.add("X-User-Data", claims.toString());
                            headers.add(HttpHeaders.AUTHORIZATION, authHeader);
                        }))
                        .build();

                System.out.println("JWT validation successful for user: " + email);

            } catch (Exception e) {
                System.out.println("JWT validation failed: " + e.getMessage());
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            System.out.println("No Authorization header found");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean isWhitelisted(String path) {
        for (String endpoint : WHITELIST) {
            // Exact match or starts with match for wildcard endpoints
            if (path.equals(endpoint) ||
                    (endpoint.endsWith("/**") && path.startsWith(endpoint.substring(0, endpoint.length() - 3))) ||
                    path.startsWith(endpoint)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return -1;
    }
}