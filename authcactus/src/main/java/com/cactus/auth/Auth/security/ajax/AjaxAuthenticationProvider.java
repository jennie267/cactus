package com.cactus.auth.Auth.security.ajax;

import com.cactus.auth.Auth.domain.User;
import com.cactus.auth.Auth.security.model.UserContext;
import com.cactus.auth.Auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public AjaxAuthenticationProvider(BCryptPasswordEncoder encoder, UserService userService) {
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Assert.notNull(authentication, "No authentication data provided");

        String id = (String)authentication.getPrincipal();
        String password = (String)authentication.getCredentials();

        User user = userService.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found : "+id));

        if(!password.equals(user.getPassword())){
            throw new BadCredentialsException("Authentication Failed. id or Password not valid.");
        }

        //BCrypt
//        if(!encoder.matches(password, user.getPassword())){
//            throw new BadCredentialsException("Authentication Failed. Email or Password not valid.");
//        }

        if(user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
                .collect(Collectors.toList());

        UserContext userContext = UserContext.create(user.getId(), authorities);
        return new UsernamePasswordAuthenticationToken(userContext,null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
