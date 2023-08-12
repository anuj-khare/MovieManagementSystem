package com.Personal.MovieManagementSystem.Service;

import com.Personal.MovieManagementSystem.Exceptions.ResourceNotFoundException;
import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;
//    public Movie findMovieById(Long id){
//        return new Optional<Movie>(movieRepository.findById(id));
//    }

    public Movie addMovie(Movie movie){
        return movieRepository.save(movie);
    }
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }
    public List<Movie> getAllMoviesByTitle(String title){
        if(movieRepository.findByTitle(title).size() >= 1){
            return movieRepository.findByTitle(title);
        }
        throw new ResourceNotFoundException("Movie","Title",title);
    }
    public List<Movie> getMoviesByGenre(Genre genre){
        return movieRepository.findByGenre(genre);
    }
//    public List<Movie> getMoviesByRatingAndGenre(Double rating,Genre genre){
//        return movieRepository.findByRatingAndGenre(rating,genre);
//    }
    public void deleteById(Long id){
        movieRepository.deleteById(id);
    }
}
