package com.cactus.server.Zuul.service;

import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<String> authCheck(String key);

}
