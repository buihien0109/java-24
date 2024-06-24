package com.example.movieapp.rest;

import com.example.movieapp.model.request.UpsertMovieRequest;
import com.example.movieapp.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/admin/movies")
@RequiredArgsConstructor
public class MovieApi {
    private final MovieService movieService;

    @PostMapping
    ResponseEntity<?> createMovie(@Valid @RequestBody UpsertMovieRequest request) {
        return ResponseEntity.ok(movieService.createMovie(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateMovie(@Valid @RequestBody UpsertMovieRequest request,
                                  @PathVariable Integer id) {
        return ResponseEntity.ok(movieService.updateMovie(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/upload-poster")
    ResponseEntity<?> uploadPoster(@PathVariable Integer id, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(movieService.uploadPoster(id, file));
    }
}
