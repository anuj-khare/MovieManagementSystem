package com.Personal.MovieManagementSystem.Service.resource;

import com.Personal.MovieManagementSystem.Model.Movie;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class reviewResponse {
    private String comment;
    private Double rating;
}
