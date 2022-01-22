package com.cactus.user.domain;

public enum RoleType {
    ADMIN, MEMBER;

    public String authority() {
        return "ROLE_" + this.name();
    }
}