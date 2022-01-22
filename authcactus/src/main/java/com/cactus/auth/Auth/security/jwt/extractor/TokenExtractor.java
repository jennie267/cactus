package com.cactus.auth.Auth.security.jwt.extractor;

public interface TokenExtractor {
    public String extract(String payload);
}
