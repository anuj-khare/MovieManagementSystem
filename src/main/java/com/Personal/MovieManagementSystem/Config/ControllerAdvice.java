package com.Personal.MovieManagementSystem.Config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidations(MethodArgumentNotValidException e){
        Map<String,String> ErrorMap = new HashMap<>();
        BindingResult result = e.getBindingResult();
        for(FieldError error : result.getFieldErrors()){
            ErrorMap.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(ErrorMap, HttpStatus.BAD_REQUEST);
    }

}
