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
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        //requestMatchers is a functional interface so we are implementing its abstract method in argument
        security.csrf().disable()
                .authorizeRequests()
                .requestMatchers(request -> {
                    return request.getServletPath().matches("/movie.*") && HttpMethod.DELETE.matches(request.getMethod());
                }).hasAuthority("admin")
                .requestMatchers(request -> {
                    return request.getServletPath().matches("/movie.*") && HttpMethod.POST.matches(request.getMethod());
                }).hasAuthority("admin")
                .requestMatchers(request -> {
                    return request.getServletPath().matches("/movie.*") && HttpMethod.GET.matches(request.getMethod());
                }).hasAnyAuthority("admin","user","OAUTH2_USER")
//                .requestMatchers(HttpMethod.POST,"/movie").hasAuthority("admin")
                .requestMatchers("/review/**").hasAnyAuthority("user","OAUTH2_USER")
                .requestMatchers("/signup/**").permitAll()
                .and().formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
                    @Override
                    public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {

                    }
                })
                .oauth2Login()
        ;
        return security.build();
    }
}
