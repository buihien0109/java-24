package com.example.movieapp.rest;

import com.example.movieapp.model.request.UpsertMovieRequest;
import com.example.movieapp.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/movies")
@RequiredArgsConstructor
public class MovieApi {
    private final MovieService movieService;

    @PostMapping
    ResponseEntity<?> createMovie(@Valid @RequestBody UpsertMovieRequest request) {
        return ResponseEntity.ok(movieService.createMovie(request));
    }
}
