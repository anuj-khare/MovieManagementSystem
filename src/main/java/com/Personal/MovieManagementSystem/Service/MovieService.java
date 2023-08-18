package com.Personal.MovieManagementSystem.Service;

import com.Personal.MovieManagementSystem.Exceptions.ResourceNotFoundException;
import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Repository.MovieRepository;
import com.Personal.MovieManagementSystem.Service.resource.movieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;
//    public Movie findMovieById(Long id){
//        return new Optional<Movie>(movieRepository.findById(id));
//    }

    public movieResponse addMovie(Movie movie){
        return movieRepository.save(movie).toMovieResponse();
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
//    public void deleteById(Long id){
//        if(!movieRepository.existsById(id)){
//            throw new ResourceNotFoundException("Movie","id",id);
//        }
//        movieRepository.deleteById(id);
//    }
    public void deleteByTitle(String Title){
        if(movieRepository.findByTitle(Title).isEmpty()){
            throw new ResourceNotFoundException("Movie","Title",Title);
        }
        movieRepository.deleteAllById(movieRepository.findByTitle(Title).stream()
                .map(x->x.getId()).collect(Collectors.toList()));
    }
}
