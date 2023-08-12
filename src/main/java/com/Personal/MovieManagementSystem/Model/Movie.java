package com.Personal.MovieManagementSystem.Model;


import com.Personal.MovieManagementSystem.Service.resource.movieResponse;
import jakarta.persistence.*;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name="movies")
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
//    @Enumerated(EnumType.ORDINAL)  // This saves values in db in the form of enum indices.
    private Genre genre;
    private Double rating;
    private String director;
    public movieResponse toMovieResponse(){
        return movieResponse.builder()
                .title(this.title)
                .rating(this.rating)
                .genre(this.genre)
                .build();
    }
}


