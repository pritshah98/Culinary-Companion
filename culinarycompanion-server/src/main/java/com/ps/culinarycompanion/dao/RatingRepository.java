package com.ps.culinarycompanion.dao;

import com.ps.culinarycompanion.ratings.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
}
