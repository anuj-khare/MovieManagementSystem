package com.Personal.MovieManagementSystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
        // NoOpPasswordEncoder is a singleton class
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        //requestMatchers is a functional interface so we are implementing its abstract method in argument
        security.csrf().disable()
                .authorizeRequests()
                .requestMatchers(request -> {
                    return request.getServletPath().equals("/movie/**") && HttpMethod.GET.matches(request.getMethod());
                }).hasAnyAuthority("admin,user")
                .requestMatchers(request -> {
                    return request.getServletPath().equals("/movie") && HttpMethod.POST.matches(request.getMethod());
                }).hasAuthority("admin")
                .requestMatchers("/review/**").hasAuthority("user")
                .requestMatchers("/signup").permitAll()
                .and().formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {

                    }
                });
        return security.build();
    }
}
