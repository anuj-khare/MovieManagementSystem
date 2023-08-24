package com.Personal.MovieManagementSystem.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//: if you don't annotate here,spring security will use its own implementation of
//AuthenticationProvider
public class MyAuthenticationManager implements AuthenticationProvider {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if(passwordEncoder.matches(authentication.getCredentials().toString(),userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getCredentials(),userDetails.getAuthorities());
        }
        throw new BadCredentialsException("Invalid Credentials");
    }

    @Override
    //If you've used multiple types of login mechanism,you need to tell which ones are supported
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
