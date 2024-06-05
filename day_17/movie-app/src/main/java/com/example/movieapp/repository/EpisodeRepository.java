package com.example.movieapp.repository;

import com.example.movieapp.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Integer> {
    List<Episode> findByMovie_IdAndStatusOrderByDisplayOrderAsc(Integer movieId, Boolean status);
}