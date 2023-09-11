package com.Personal.MovieManagementSystem.Model;


import com.Personal.MovieManagementSystem.Service.resource.movieResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name="movies")
@Builder
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
//    @Enumerated(EnumType.ORDINAL)  // This saves values in db in the form of enum indices.
    private Genre genre;
    private String director;
    //This is a OneToMany Relationship : 1 movie can have multiple reviews
    private LocalDate releaseDate;
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL)
    List<Review> reviewList;
    public movieResponse toMovieResponse(){
        return movieResponse.builder()
                .title(this.title)
                .genre(this.genre)
                .releaseDate(releaseDate.toString())
                .reviewList(this.reviewList)
                .build();
    }
}


/*
 Association b/w Movie and Review : 1 Movie -> Muliple Reviews
 1 Review -> 1 Movie
 Many reviews -> 1 Movie
 ==> OneToMany  (from Movie side)
 ==> ManyToOne  (from Review side)
 */