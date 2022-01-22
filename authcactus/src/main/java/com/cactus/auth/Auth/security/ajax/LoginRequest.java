package com.cactus.auth.Auth.security.ajax;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {
    private String id;
    private String password;

    @JsonCreator
    public LoginRequest(@JsonProperty("id") String id, @JsonProperty("password") String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
