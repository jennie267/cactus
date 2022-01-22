package com.cactus.auth.Auth.domain;

public enum RoleType {
    ADMIN, MEMBER;

    public String authority() {
        return "ROLE_" + this.name();
    }
}