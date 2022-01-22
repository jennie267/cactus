package com.cactus.auth.Auth.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.List;

public class UserContext {
    private final String id;
    private final List<GrantedAuthority> authorities;

    public UserContext(String id, List<GrantedAuthority> authorities) {
        this.id = id;
        this.authorities = authorities;
    }

    public static UserContext create(String id, List<GrantedAuthority> authorities){
        if(StringUtils.isEmpty(id)) throw new IllegalArgumentException("Email is blank: "+ id);
        return new UserContext(id, authorities);
    }

    public String getId() {
        return id;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
