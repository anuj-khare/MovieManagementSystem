package com.Personal.MovieManagementSystem.Service;

import com.Personal.MovieManagementSystem.Exceptions.ResourceNotFoundException;
import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Repository.MovieRepository;
import com.Personal.MovieManagementSystem.Service.resource.movieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    RedisTemplate redisTemplate;
    private final String homepageMovieKey = "Home";

    public Movie addMovie(Movie movie){
        if(movie.getReleaseDate().isAfter(LocalDate.now().minusDays(30))){
            redisTemplate.opsForList().rightPush(homepageMovieKey,movie);
        }
        return movieRepository.save(movie);
    }
    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }
    public List<Movie> getMoviesForHomePage(){
        List<Movie> movieList = redisTemplate.opsForList().range(homepageMovieKey,0,-1);
        if(CollectionUtils.isEmpty(movieList)){
            movieList = movieRepository.findAllByOrderByReleaseDate();
            if(!CollectionUtils.isEmpty(movieList)){
                movieList.stream().forEach(movie->redisTemplate.opsForList().rightPush(homepageMovieKey,movie));
            }
        }
        return movieList;
    }
    public List<Movie> getAllMoviesByTitle(String title){
        if(!movieRepository.findByTitle(title).isEmpty()){
            return movieRepository.findByTitle(title);
        }
        throw new ResourceNotFoundException("Movie","Title",title);
    }
    public List<Movie> getMoviesByGenre(Genre genre){
        return movieRepository.findByGenre(genre);
    }
    public void deleteByTitle(String Title){
        if(movieRepository.findByTitle(Title).isEmpty()){
            throw new ResourceNotFoundException("Movie","Title",Title);
        }
        movieRepository.deleteAllById(movieRepository.findByTitle(Title).stream()
                .map(x->x.getId()).collect(Collectors.toList()));
    }

    public List<Movie> getTopMovies(Genre genre) {
        return movieRepository.getTopMovies(genre);
    }
}
