package com.cactus.auth.Auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Auth
 * Class: User
 * Created by hapo on 2019-11-22.
 * Description:
 */

@Setter
@Getter
public class User {

    String id;
    String password;
    private List<Role> roles;

    User(){

    }

    @Builder
    public User(String id, String password, List<Role> roles) {
        this.id = id;
        this.password = password;
        this.roles = roles;
    }
}
