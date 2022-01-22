package com.cactus.auth.Auth.service;

import com.cactus.auth.Auth.domain.User;

import java.util.Optional;

/**
 * Auth
 * Class: UserService
 * Created by hapo on 2019-11-22.
 * Description:
 */
public interface UserService {

    Optional<User> findById(String id);
}
