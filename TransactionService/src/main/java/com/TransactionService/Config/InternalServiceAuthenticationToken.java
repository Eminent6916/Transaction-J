package com.TransactionService.Config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Custom authentication token representing a trusted internal service.
 * This grants the service full access (ROLE_SERVICE) upon successful key validation.
 */
public class InternalServiceAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    public InternalServiceAuthenticationToken(Object principal) {
        // Grant ROLE_SERVICE authority to allow access to secured internal endpoints
        super(AuthorityUtils.createAuthorityList("ROLE_SERVICE"));
        this.principal = principal;
        // Mark as authenticated since the internal key is verified
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        // Credentials (the token/key) are typically hidden once authenticated
        return null;
    }

    @Override
    public Object getPrincipal() {
        // Principal is the identifier of the calling service
        return this.principal;
    }
}
