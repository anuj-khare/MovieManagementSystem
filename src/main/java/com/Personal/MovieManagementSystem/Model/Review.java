package com.Personal.MovieManagementSystem.Model;

import com.Personal.MovieManagementSystem.Service.resource.reviewResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews")
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Double rating;
    private String comment;
    //This is a ManyToOne relationship : Multiple Reviews for a single movie
    @ManyToOne
    @JoinColumn(name = "movie_id",nullable=false)
    @JsonIgnore
    // tells to not load this property (so as to avoid circular dependency)
    private Movie movie;//It will merge with id of movie class and store the column name as movie_id
    public reviewResponse toReviewResponse(){
        return reviewResponse.builder()
                .rating(this.rating)
                .comment(this.comment)
                .movie(this.movie)
                .build();
    }
}


/*
 1 Review -> 1 Movie
 Many Reviews -> 1 Movie
 ==> ManyToOne from Review Side
 */