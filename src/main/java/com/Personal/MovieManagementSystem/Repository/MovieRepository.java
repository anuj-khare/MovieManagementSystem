package com.Personal.MovieManagementSystem.Repository;

import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long>{
    //List<Movie> findAll();
    List<Movie> findByTitle(String title);
    //List<Movie> findByDirector(String director);
    List<Movie> findByGenre(Genre genre);
    //List<Movie> findByRatingAndGenre(Double rating,Genre genre);

    //@Query("Select m from Movie m where genre=?1")
    //List<Movie> promotionMovies(Genre genre);

    //@Query(value = "Select * from movies where genre = ?",nativeQuery = true)
//    List<Movie> genreView(Genre genre); ->This will throw an error because you have told hibernate
    //not to convert your native queries but in database there is no genre field,it is only varchar.
    //So the solution to this is to pass the genre parameter as a string.
    //@Query(value = "Select * from movies where genre = ?",nativeQuery = true)
    //List<Movie> genreView(String genre);

}



/*
Important -> @Component,@Service,@Repository:
        @Component marks a class to be maintained by spring
        @Repository is a special case of @Component,This annotation catches persistence
        specific exceptions and rethrow them as one of spring's unified unchecked exceptions.
        @Service marks a class on service layer,No other use than that.
 */

/*
    You should try to avoid using native queries as much as can,
    This is because you don't want to do db operations directly from Java-classes
    and maintain loose coupling as much as you can.So,try to use either findByProperty way of Hibernate
    or write using @Query and provide JPQL queries.
 */