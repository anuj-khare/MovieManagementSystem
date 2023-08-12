package com.Personal.MovieManagementSystem.Controller;

import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Service.MovieService;
import com.Personal.MovieManagementSystem.Service.resource.movieRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/movie")
public class MovieController {
    @Autowired
    MovieService movieService;
    @PostMapping
    public ResponseEntity<?> addMovie(@RequestBody @Valid movieRequest movie){
        return new ResponseEntity<>(movieService.addMovie(movie.getMovieFromRequest()).toMovieResponse(),HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<?> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovies(),HttpStatus.OK);
    }
    @GetMapping(value = "{Title}")
    public ResponseEntity<?> getMovie(@PathVariable("Title") String Title){
        return new ResponseEntity<>(movieService.getAllMoviesByTitle(Title),HttpStatus.OK);
    }


//    @GetMapping(params = {"id"})
//    public ResponseEntity<?> getStudentById(@RequestParam int id) {
//        return new ResponseEntity<>(movieService.,HttpStatus.OK);
//    }

    @GetMapping(path = "genre/{genre}")
    public ResponseEntity<?> getMoviesByGenre(@PathVariable("genre") Genre Genre){
        return new ResponseEntity<>(movieService.getMoviesByGenre(Genre),HttpStatus.OK);
    }

//    @DeleteMapping(value = "{Title}")
//    public ResponseEntity<?> deleteMovie(@PathVariable String Title){
//        return new ResponseEntity<>("deleted",HttpStatus.OK);
//    }
//    @PutMapping(value = "{id}")
//    public ResponseEntity<?> updateMovie(@RequestBody Movie movie,@PathVariable int id){
//        return new ResponseEntity<>(movie,HttpStatus.OK);
//    }
}
