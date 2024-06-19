package com.example.movieapp.service;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.entity.Review;
import com.example.movieapp.entity.User;
import com.example.movieapp.exception.ResourceNotFoundException;
import com.example.movieapp.model.request.CreateReviewRequest;
import com.example.movieapp.model.request.UpdateReviewRequest;
import com.example.movieapp.repository.MovieRepository;
import com.example.movieapp.repository.ReviewRepository;
import com.example.movieapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public List<Review> getReviewsByMovie(Integer id) {
        return reviewRepository.findByMovie_IdOrderByCreatedAtDesc(id);
    }

    // TODO: Validation hướng dẫn sau (SpringBoot Validation)
    public Review createReview(CreateReviewRequest request) {
        // TODO: Fix user. Về sau user chính là user đang đăng nhập
        Integer userId = 1;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

        Review review = Review.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(user)
                .movie(movie)
                .build();

        reviewRepository.save(review);
        return review;
    }

    public Review updateReview(Integer id, UpdateReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // TODO: Fix user. Về sau user chính là user đang đăng nhập
        Integer userId = 1;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can't update this review");
        }

        review.setContent(request.getContent());
        review.setRating(request.getRating());
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);

        return review;
    }

    public void deleteReview(Integer id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // TODO: Fix user. Về sau user chính là user đang đăng nhập
        Integer userId = 1;
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can't delete this review");
        }

        reviewRepository.delete(review);
    }
}
