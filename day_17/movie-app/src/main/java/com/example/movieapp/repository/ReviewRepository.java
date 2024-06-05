package com.example.movieapp.repository;

import com.example.movieapp.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByMovie_IdOrderByCreatedAtDesc(Integer movieId);
}