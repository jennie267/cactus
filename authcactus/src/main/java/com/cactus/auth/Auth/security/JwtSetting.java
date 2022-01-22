package com.cactus.auth.Auth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * freight
 * Class: JwtSetting
 * Created by hapo on 2019-02-01.
 * Description: JwtSetting
 */
@Configuration
public class JwtSetting {
    @Value("${app.cactus.auth.tokenIssuer}")
    private String tokenIssuer;
    @Value("${app.cactus.auth.secretKey}")
    private String tokenSigningKey;
    @Value("${app.cactus.auth.tokenExpirationTime}")
    private Integer tokenExpirationTime;
    @Value("${app.cactus.auth.refreshTokenExpTime}")
    private Integer refreshTokenExpTime;

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }
}
