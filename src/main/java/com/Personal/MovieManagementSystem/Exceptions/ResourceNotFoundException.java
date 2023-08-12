package com.Personal.MovieManagementSystem.Exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Getter
public class ResourceNotFoundException extends RuntimeException{
    private String Resource;
    private String Property;
    private Object PropertyValue;
    public ResourceNotFoundException(String Resource, String Property,Object PropertyValue) {
        super(String.format("%s not found with %s : %s",Resource,Property,PropertyValue));
        this.Resource = Resource;
        this.Property = Property;
        this.PropertyValue = PropertyValue;
    }
}
