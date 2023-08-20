package com.Personal.MovieManagementSystem.Model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class MyUser implements UserDetails {
    private String userName;
    private String password;
    private String authorities; // USER,ADMIN etc
    public MyUser(String userName, String password, String authorities){
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(StringUtils.hasText(authorities))
        return Arrays.stream(authorities.split(",")).map(x->new SimpleGrantedAuthority(x)).collect(Collectors.toList());
        return Collections.emptyList();
        // Output : [ADMIN,USER]
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
