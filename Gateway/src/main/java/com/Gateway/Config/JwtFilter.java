package com.Gateway.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.nio.charset.StandardCharsets;

@Component
public class JwtFilter implements GlobalFilter, Ordered {

    @Value("${jwt.secret}")
    private String SECRET;

    // Corrected whitelist - use consistent path patterns
    private static final String[] WHITELIST = {
            "/api/v1/users/auth/sendVerification",
            "/api/v1/users/auth/login",
            "/api/v1/users/auth/otpVerification",
            "/api/v1/users/createPassword",
//            "/api/v1/users/auth/**"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        System.out.println("JWT Filter - Path: " + path + ", Method: " + method);

        if (isWhitelisted(path)) {
            System.out.println("Whitelisted path - skipping JWT validation: " + path);
            return chain.filter(exchange);
        }

        System.out.println("Protected path - validating JWT: " + path);

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
                return chain.filter(exchange);

            } catch (ExpiredJwtException e) {
                System.out.println("JWT validation failed: Token Expired. " + e.getMessage());
                return setErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Token Expired.");
            }
            catch (Exception e) {
                System.out.println("JWT validation failed: Invalid Token. " + e.getMessage());
                return setErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Invalid Token.");
            }
        } else {
            System.out.println("No Authorization header found or malformed");
            return setErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Authorization header is missing or invalid.");
        }
    }

    /**
     * Helper method to set a specific error response.
     */
    private Mono<Void> setErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String errorJson = "{\"status\": " + status.value() + ", \"error\": \"" + status.getReasonPhrase() + "\", \"message\": \"" + message + "\"}";
        byte[] bytes = errorJson.getBytes(StandardCharsets.UTF_8);

        return exchange.getResponse().writeWith(
                        Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)))
                .then(Mono.empty());
    }

    private boolean isWhitelisted(String path) {
        for (String endpoint : WHITELIST) {
            if (path.equals(endpoint) || path.startsWith(endpoint)) {
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