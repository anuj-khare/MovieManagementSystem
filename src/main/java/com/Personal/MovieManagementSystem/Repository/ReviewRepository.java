package com.Personal.MovieManagementSystem.Repository;

import com.Personal.MovieManagementSystem.Model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
//    public Review save(Review review);
}
