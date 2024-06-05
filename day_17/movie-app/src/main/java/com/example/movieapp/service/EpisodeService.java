package com.example.movieapp.service;

import com.example.movieapp.entity.Episode;
import com.example.movieapp.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;

    public List<Episode> getEpisodeListOfMovie(Integer movieId) {
        return episodeRepository.findByMovie_IdAndStatusOrderByDisplayOrderAsc(movieId, true);
    }
}
