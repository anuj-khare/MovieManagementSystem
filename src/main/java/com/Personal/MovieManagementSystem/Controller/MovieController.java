package com.Personal.MovieManagementSystem.Controller;

import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Service.MovieService;
import com.Personal.MovieManagementSystem.Service.resource.movieRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/movie")
public class MovieController {
    @Autowired
    MovieService movieService;
    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody @Valid movieRequest movie){
        return new ResponseEntity<>(movieService.addMovie(movie.getMovieFromRequest()),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovies()
                .stream().map(x->x.toMovieResponse()).collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping(value = "{Title}")
    public ResponseEntity<?> getMovie(@PathVariable("Title") String Title){
        return new ResponseEntity<>(movieService.getAllMoviesByTitle(Title).stream()
                .map(x->x.toMovieResponse()).collect(Collectors.toList()), HttpStatus.OK);
    }


//    @GetMapping(params = {"id"})
//    public ResponseEntity<?> getStudentById(@RequestParam int id) {
//        return new ResponseEntity<>(movieService.,HttpStatus.OK);
//    }

    @GetMapping(path = "genre/{genre}")
    public ResponseEntity<?> getMoviesByGenre(@PathVariable("genre") Genre Genre){
        return new ResponseEntity<>(movieService.getMoviesByGenre(Genre),HttpStatus.OK);
    }

    @DeleteMapping(path = "{Title}")
    public ResponseEntity<?> deleteMovie(@PathVariable("Title") String Title){
        movieService.deleteByTitle(Title);
        return new ResponseEntity<>("Movie(s) with Title : "+Title+" have been deleted",HttpStatus.OK);
    }
}
