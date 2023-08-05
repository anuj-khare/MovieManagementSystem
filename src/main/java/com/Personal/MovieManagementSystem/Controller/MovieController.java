package com.Personal.MovieManagementSystem.Controller;

import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping(value="/movie")
public class MovieController {
    @Autowired
    MovieService movieService;
    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody @Valid Movie movie){
        return new ResponseEntity<>(movieService.addMovie(movie),HttpStatus.CREATED);
    }
    @GetMapping(value = "{Title}")
    public ResponseEntity<?> getMovie(@PathVariable("Title") String Title){
        return new ResponseEntity<>(movieService.getMovie(Title),HttpStatus.OK);
    }
    @GetMapping
    public List<Movie> getAllMovies(){
        return movieService.getAllMovies();
    }

    @GetMapping(params = {"id"})
    public ResponseEntity<?> getStudentById(@RequestParam int id) {
        return new ResponseEntity<>(movieService.getMovieById(id),HttpStatus.OK);
    }

    @GetMapping(path = "genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable("genre") String Genre){
        return movieService.getTopMoviesInEachGenre(Genre);
    }

    @DeleteMapping(value = "{Title}")
    public ResponseEntity<?> deleteMovie(@PathVariable String Title){
        return new ResponseEntity<>(movieService.deleteByTitle(Title),HttpStatus.OK);
    }
    @PutMapping(value = "{id}")
    public ResponseEntity<?> updateMovie(@RequestBody Movie movie,@PathVariable int id){
        return new ResponseEntity<>(movieService.updateMovie(movie,id),HttpStatus.OK);
    }
}
