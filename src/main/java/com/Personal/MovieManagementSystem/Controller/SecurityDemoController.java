package com.Personal.MovieManagementSystem.Controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityDemoController {
    @GetMapping("/greet/{user}")
    public ResponseEntity<?> greetUser(@PathVariable String user){
        return new ResponseEntity<>("Hello : "+user, HttpStatusCode.valueOf(200));
    }
    @GetMapping("/user/details")
    public ResponseEntity<?> getOAuth2UserDetails(Authentication authentication){
        return new ResponseEntity<>(authentication,HttpStatusCode.valueOf(200));
    }
}
