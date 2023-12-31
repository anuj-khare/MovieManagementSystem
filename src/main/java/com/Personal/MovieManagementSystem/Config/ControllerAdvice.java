package com.Personal.MovieManagementSystem.Config;

import com.Personal.MovieManagementSystem.Exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.format.DateTimeParseException;
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
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleGenreException(HttpMessageNotReadableException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<?> handleDateParsingErrors(DateTimeParseException e){
        return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<?> handleArgumentMismatchError(MethodArgumentTypeMismatchException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
