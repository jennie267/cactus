package com.cactus.auth.Auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * freight
 * Class: Role
 * Created by hapo on 2019-02-01.
 * Description:
 */

@Getter
@Setter
public class Role {
    private Long userRoleId;
    protected RoleType role;

    Role(){

    }

    @Builder
    public Role(RoleType role) {
        this.role = role;
    }
}
