package com.Personal.MovieManagementSystem.Model;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    private Integer id;
    @NotBlank(message="Title cannot be blank")
    private String title;
    @NotBlank(message="Genre cannot be blank")
    private String genre;
    @Min(value = 0,message = "Rating cannot be negative")@Max(value = 5,message = "Rating cannot exceed 5.0")
    private Double rating;
}
