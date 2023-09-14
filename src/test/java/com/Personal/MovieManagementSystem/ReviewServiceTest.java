package com.Personal.MovieManagementSystem;

import com.Personal.MovieManagementSystem.Exceptions.ResourceNotFoundException;
import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Model.Review;
import com.Personal.MovieManagementSystem.Repository.MovieRepository;
import com.Personal.MovieManagementSystem.Repository.ReviewRepository;
import com.Personal.MovieManagementSystem.Service.ReviewService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReviewServiceTest {
    @InjectMocks
    ReviewService reviewService;
    @Mock
    ReviewRepository reviewRepository;
    @Mock
    MovieRepository movieRepository;
    @Before
    public void setup(){
        reviewRepository = mock(ReviewRepository.class);
        movieRepository = mock(MovieRepository.class);
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void addReviewTest(){
        Movie m = new Movie();
        m.setId(1L);
        Review review = new Review();
        review.setMovie(m);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(m));
        when(reviewRepository.save(review)).thenReturn(review);
        Review savedReview = reviewService.addReview(review);
        verify(movieRepository,times(1)).findById(1L);
        verify(reviewRepository,times(1)).save(review);
        assertEquals(review,savedReview);
    }
    @Test
    public void addReviewInvalidMovieIdTest(){
        Review review = new Review();
        review.setMovie(new Movie());
        review.getMovie().setId(1L);
        //assuming no Movie with id 1L exists
        when(movieRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->{
         reviewService.addReview(review);
        });
        verify(movieRepository,times(1)).findById(1L);
    }
    @Test
    public void findByIdTest(){
        Review review = new Review();
        review.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        Review actualReview = reviewService.findById(1L);
        verify(reviewRepository,times(1)).findById(1L);
        assertEquals(review,actualReview);
    }
    @Test(expected = ResourceNotFoundException.class)
    public void findReviewInvalidIdTest(){
        Review review = new Review();
        review.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());
        reviewService.findById(1L);
        verify(reviewRepository,times(1)).findById(1L);
    }
    @Test
    public void getAllReviewsTest(){
        Review review = new Review();
        Review review2 = new Review();
        List<Review> reviewList = new ArrayList<>();
        when(reviewRepository.findAll()).thenReturn(reviewList);
        reviewList.add(review);
        reviewList.add(review2);
        assertEquals(reviewList,reviewService.getAllReviews());
    }
    @Test
    public void deleteReviewTest(){
        Review review = new Review();
        review.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        reviewService.deleteReview(1L);
        verify(reviewRepository,times(1)).findById(1L);
        verify(reviewRepository,times(1)).delete(review);
    }
    @Test(expected = ResourceNotFoundException.class)
    public void deleteReviewInvalidIdTest(){
        Review review = new Review();
        review.setId(1L);
        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());
        reviewService.deleteReview(1L);
    }

}
