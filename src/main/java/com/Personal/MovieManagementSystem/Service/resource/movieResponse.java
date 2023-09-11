package com.Personal.MovieManagementSystem.Service.resource;

import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Model.Review;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class movieResponse {
    private String title;
    private Genre genre;
    private String releaseDate;
    private List<Review> reviewList;
}
