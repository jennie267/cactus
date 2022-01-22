package com.cactus.auth.Auth.controller;

import com.cactus.auth.Auth.WebSecurityConfig;
import com.cactus.auth.Auth.domain.User;
import com.cactus.auth.Auth.security.JwtSetting;
import com.cactus.auth.Auth.security.exceptions.InvalidJwtToken;
import com.cactus.auth.Auth.security.jwt.extractor.TokenExtractor;
import com.cactus.auth.Auth.security.jwt.verifier.TokenVerifier;
import com.cactus.auth.Auth.security.model.UserContext;
import com.cactus.auth.Auth.security.model.token.JwtToken;
import com.cactus.auth.Auth.security.model.token.JwtTokenFactory;
import com.cactus.auth.Auth.security.model.token.RawAccessJwtToken;
import com.cactus.auth.Auth.security.model.token.RefreshToken;
import com.cactus.auth.Auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Auth
 * Class: TokenController
 * Created by hapo on 2019-11-22.
 * Description:
 */

@RestController
@RequestMapping(value = "/auth")
public class TokenController {
    public static final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @Autowired
    private JwtTokenFactory tokenFactory;
    @Autowired private JwtSetting jwtSetting;
    @Autowired private UserService userService;
    @Autowired private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;


    @GetMapping(value = "/test")
    public String tokenCheck(){

        return "SUCCESS";
    }

    @GetMapping(value="/token", produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody
    JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));

        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);

        RefreshToken refreshToken = RefreshToken.create(rawToken,jwtSetting.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if(!tokenVerifier.verify(jti)){
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
        User user = userService.findById(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if(user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getId(), authorities);

        return tokenFactory.createAccessJwtToken(userContext);

    }
}
