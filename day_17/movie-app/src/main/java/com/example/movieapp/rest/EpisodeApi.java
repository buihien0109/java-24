package com.example.movieapp.rest;

import com.example.movieapp.service.EpisodeService;
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
}
