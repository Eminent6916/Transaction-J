package com.TransactionService.Config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

/**
 * Feign Client Interceptor that injects an internal authorization token
 * into all outgoing requests to other microservices (like User Service).
 */
@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    // IMPORTANT: Define this key in your transaction-service application.yml/properties
    // It MUST match the key defined in the User Service's internalAuthToken property.
    @Value("${service.internal-auth-token:INTERNAL_SECRET_KEY_MUST_BE_SECURED}")
    private String internalAuthToken;

    @Override
    public void apply(RequestTemplate template) {
        // Add the shared secret token to the Authorization header
        // This ensures the User Service's InternalServiceAuthFilter passes the request.
        template.header(HttpHeaders.AUTHORIZATION, "Bearer " + internalAuthToken);
    }
}
