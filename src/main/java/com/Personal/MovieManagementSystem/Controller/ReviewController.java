package com.Personal.MovieManagementSystem.Controller;

import com.Personal.MovieManagementSystem.Exceptions.ResourceNotFoundException;
import com.Personal.MovieManagementSystem.Model.Review;
import com.Personal.MovieManagementSystem.Service.ReviewService;
import com.Personal.MovieManagementSystem.Service.resource.reviewRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @PostMapping
    public ResponseEntity<?> addreview(@RequestBody @Valid reviewRequest review){
        return new ResponseEntity<>(reviewService.addReview(review.toReview()).toReviewResponse(), HttpStatus.CREATED);
    }
    @GetMapping(value = "{id}")
    public ResponseEntity<?> getReview(@PathVariable("id") Long id){
        return new ResponseEntity<>(reviewService.findById(id).toReviewResponse(),HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllReviews(){
        if(reviewService.getAllReviews().isEmpty()){
            throw new ResourceNotFoundException("No Reviews found","any movies","List Empty");
        }
        return new ResponseEntity<>(reviewService.getAllReviews()
                .stream().map(x->x.toReviewResponse()).collect(Collectors.toList()), HttpStatus.OK);
    }
    @PutMapping(value = "{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id,@RequestBody Review review){
        return new ResponseEntity<>(reviewService.updateReview(id,review),HttpStatus.OK);
    }
    @DeleteMapping(value="{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id){
        reviewService.deleteReview(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
