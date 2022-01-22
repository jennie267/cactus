package com.cactus.auth.Auth.security.jwt.verifier;

public interface TokenVerifier {
    public boolean verify(String jti);
}
