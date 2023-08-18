package com.Personal.MovieManagementSystem.Service;

import com.Personal.MovieManagementSystem.Exceptions.ResourceNotFoundException;
import com.Personal.MovieManagementSystem.Model.Movie;
import com.Personal.MovieManagementSystem.Model.Review;
import com.Personal.MovieManagementSystem.Repository.MovieRepository;
import com.Personal.MovieManagementSystem.Repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    Logger logger = LoggerFactory.getLogger(ReviewService.class);
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    MovieRepository movieRepository;


//    public boolean addReview(Review review) {
//        try{

//            reviewRepository.save(review);
//            return true;
//        }
//        catch(Exception e){
//            logger.error("cannot save the review :{}",e.getMessage());
//        }
//        return false;
//    }
    public Review addReview(Review review){
        Movie movie = movieRepository.findById(review.getMovie().getId()).orElseThrow(
                () -> new ResourceNotFoundException("Movie","id",review.getMovie().getId())
        );
        review.setMovie(movie);
        return reviewRepository.save(review);
    }

    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Review","id",id)
        );
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(Long id, Review review) {
        Review persistedReview = reviewRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Review","id",id)
        );
        persistedReview.setRating(review.getRating());
        persistedReview.setComment(review.getComment());
        return reviewRepository.save(persistedReview);
    }

    public void deleteReview(Long id) {
        Review persistedReview = reviewRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Review","id",id)
        );
        reviewRepository.delete(persistedReview);
    }
}
