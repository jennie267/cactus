package com.cactus.server.Zuul.service;


import com.cactus.server.Zuul.util.RestTemplateUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public ResponseEntity<String> authCheck(String key) {

        ResponseEntity<String> result = RestTemplateUtil.getResponseEntity("http://authcactus.paas-ta.org/auth/test", key);

        return result;
    }
}
