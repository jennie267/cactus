package com.cactus.auth.Auth.service;

import com.cactus.auth.Auth.domain.User;
import com.cactus.auth.Auth.util.RestTemplateUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * Auth
 * Class: UserServiceImpl
 * Created by hapo on 2019-11-22.
 * Description:
 */

@Service
public class UserServiceImpl implements UserService {

    @Override
    public Optional<User> findById(String id) {
        User user = RestTemplateUtil.getUserById(id).getBody();
        return Optional.of(user);
    }
}
