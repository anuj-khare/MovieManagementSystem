package com.Personal.MovieManagementSystem.Service.resource;

import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Model.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class reviewRequest {
    @Min(value = 0,message = "Rating cannot be negative ")
    @Max(value = 5,message = "Rating cannot be more than 5")
    @NotNull(message = "rating cannot be left blank")
    private Double rating;
    private String comment;
    private Long movieId;
    public Review toReview(){
        return Review.builder().rating(this.rating).comment(this.comment)
               .movie(Movie.builder().id(this.movieId).build())
                .build();
    }

}
