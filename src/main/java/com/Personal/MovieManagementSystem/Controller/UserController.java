package com.Personal.MovieManagementSystem.Controller;

import com.Personal.MovieManagementSystem.Service.MyUserDetailService;
import com.Personal.MovieManagementSystem.Service.resource.userRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    MyUserDetailService userService;
    @PostMapping(value = "/signup")
    public ResponseEntity<?> addUser(@RequestBody @Valid userRequest user){
        userService.addUser(user.getUserFromRequest());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
