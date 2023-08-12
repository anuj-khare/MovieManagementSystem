package com.Personal.MovieManagementSystem.Service.resource;

import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Model.Movie;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class movieRequest {
    @NotBlank(message = "title cannot be left blank")
    private String title;
    private Genre genre;
    public Movie getMovieFromRequest(){
        return Movie.builder()
                .title(this.title)
                .rating(3.1)
                .genre(this.genre)
                .build();
    }
}
