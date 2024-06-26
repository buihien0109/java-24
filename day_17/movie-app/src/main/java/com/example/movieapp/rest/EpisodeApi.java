package com.example.movieapp.rest;

import com.example.movieapp.model.request.CreateEpisodeRequest;
import com.example.movieapp.model.request.UpdateEpisodeRequest;
import com.example.movieapp.service.EpisodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/admin/episodes")
@RequiredArgsConstructor
public class EpisodeApi {
    private final EpisodeService episodeService;

    @PostMapping("/{id}/upload-video")
    ResponseEntity<?> uploadVideo(@PathVariable Integer id, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(episodeService.uploadVideo(id, file));
    }

    @PostMapping
    ResponseEntity<?> createEpisode(@Valid @RequestBody CreateEpisodeRequest request) {
        return ResponseEntity.ok(episodeService.createEpisode(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateEpisode(@Valid @RequestBody UpdateEpisodeRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(episodeService.updateEpisode(id, request));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteEpisode(@PathVariable Integer id) {
        episodeService.deleteEpisode(id);
        return ResponseEntity.ok().build();
    }
}
