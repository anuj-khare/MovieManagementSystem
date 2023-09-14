package com.Personal.MovieManagementSystem;

import com.Personal.MovieManagementSystem.Exceptions.ResourceNotFoundException;
import com.Personal.MovieManagementSystem.Model.Genre;
import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Repository.MovieRepository;
import com.Personal.MovieManagementSystem.Service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {
    @InjectMocks
    private MovieService movieService;
    @Mock
    private MovieRepository movieRepository;
    @InjectMocks
    private RedisTemplate redisTemplate;
    @Mock
    ListOperations<String,Movie> listOperations;
    @Before
    public void setup(){
        movieRepository= mock(MovieRepository.class);
        redisTemplate = mock(RedisTemplate.class);
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void AddMovieWithinLast30Days(){
        Movie movie = new Movie();
        movie.setReleaseDate(LocalDate.now().minusDays(10));//redisTemplate must be called once
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(movieRepository.save(movie)).thenReturn(movie);
        doReturn(null).when(listOperations).rightPush(anyString(),any(Movie.class));
        Movie savedMovie = movieService.addMovie(movie);
        verify(redisTemplate.opsForList(),times(1)).rightPush(anyString(),any(Movie.class));
        assertEquals(movie,savedMovie);
    }
    @Test
    public void AddMovieOldReleaseDate(){
        Movie movie = new Movie();
        movie.setReleaseDate(LocalDate.now().minusDays(100));//redisTemplate must be called once
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(movieRepository.save(movie)).thenReturn(movie);
        Movie savedMovie = movieService.addMovie(movie);
        verify(redisTemplate.opsForList(),times(0)).rightPush(anyString(),any(Movie.class));
        assertEquals(movie,savedMovie);
    }
    @Test
    public void getAllMoviesTest(){
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        movieList.add(new Movie());
        when(movieRepository.findAll()).thenReturn(movieList);
        List<Movie> actualMovie = movieService.getAllMovies();
        assertEquals(movieList,actualMovie);
    }
    @Test
    public void getMoviesForHomePageCacheHitTest(){
        //Mocking the case where data is coming from redis-cache
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        when(listOperations.range("Home",0,-1)).thenReturn(movieList);
        List<Movie> actualMovies = movieService.getMoviesForHomePage();
        assertEquals(movieList,actualMovies);
        verify(movieRepository,times(0)).findAllByOrderByReleaseDate();
        verify(redisTemplate,times(1)).opsForList();
        verify(listOperations,times(1)).range("Home",0,-1);
    }
    @Test
    public void getMoviesForHomePageCacheMissTest(){
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        movieList.add(new Movie());
        //Mocking the case where data is not coming from redis-cache
        when(redisTemplate.opsForList()).thenReturn(listOperations);
        when(listOperations.range("Home",0,-1)).thenReturn(new ArrayList<>());
        when(movieRepository.findAllByOrderByReleaseDate()).thenReturn(movieList);
        List<Movie> actualMovies = movieService.getMoviesForHomePage();
        assertEquals(movieList,actualMovies);
        verify(movieRepository,times(1)).findAllByOrderByReleaseDate();
        verify(redisTemplate,times(movieList.size() + 1)).opsForList();
        verify(listOperations,times(1)).range("Home",0,-1);
    }
    @Test
    public void getMoviesByTitleTest(){
        List<Movie> movieList = new ArrayList<>();
        Movie m1 = new Movie();
        Movie m2 = new Movie();
        m1.setTitle("ABCD");
        movieList.add(m1);
        movieList.add(m2);
        when(movieRepository.findByTitle("ABCD")).thenReturn(movieList);
        assertEquals(movieList,movieService.getAllMoviesByTitle("ABCD"));
    }
    @Test(expected = ResourceNotFoundException.class)
    public void getMoviesByInvalidTitleTest(){
        when(movieRepository.findByTitle("abcd")).thenReturn(new ArrayList<>());
        movieService.getAllMoviesByTitle("abcd");
    }
    @Test
    public void getMoviesByGenre(){
        List<Movie> movieList = new ArrayList<>();
        Movie movie = new Movie();
        movie.setGenre(Genre.COMEDY);
        movieList.add(movie);
        when(movieRepository.findByGenre(Genre.COMEDY)).thenReturn(movieList);
        assertEquals(movieList,movieService.getMoviesByGenre(Genre.COMEDY));
    }
    @Test
    public void GetMoviesByGenreNoMovieTest(){
        when(movieRepository.findByGenre(Genre.ACTION)).thenReturn(new ArrayList<>());
        assertEquals(0,movieService.getMoviesByGenre(Genre.ACTION).size());
    }
    @Test
    public void GetMoviesByGenreInvalidGenreTest(){
        when(movieRepository.findByGenre(any())).thenReturn(null);
        assertNull(movieService.getMoviesByGenre(Genre.ACTION));
    }
    @Test
    public void deleteByTitleTest(){
        Movie movie = new Movie();
        movie.setTitle("ABCD");
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        when(movieRepository.findByTitle("ABCD")).thenReturn(movieList);
        movieService.deleteByTitle("ABCD");
        verify(movieRepository,times(2)).findByTitle("ABCD");
        verify(movieRepository,times(1)).deleteAllById(anyList());
    }
    @Test(expected = ResourceNotFoundException.class)
    public void deleteByInvalidTitleTest(){
        Movie movie = new Movie();
        movie.setTitle("ABCD");
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        movieService.deleteByTitle("ABCD");
    }
    @Test
    public void getTopMoviesTestInEachGenreTest(){
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        when(movieRepository.getTopMovies(Genre.ACTION)).thenReturn(movieList);
        List<Movie> actualTopMoviesList = movieService.getTopMovies(Genre.ACTION);
        assertEquals(2,actualTopMoviesList.size());
    }
    @Test
    public void getTopMoviesNoMoviesWithGenreTest(){
        when(movieRepository.getTopMovies(Genre.ACTION)).thenReturn(null);
        assertNull(movieService.getTopMovies(Genre.ACTION));
    }
    @Test(expected = MethodArgumentTypeMismatchException.class)
    public void getTopMoviesInvalidGenre(){
        when(movieRepository.getTopMovies(any(Genre.class))).thenThrow(MethodArgumentTypeMismatchException.class);
        movieService.getTopMovies(Genre.ACTION);
    }

}
