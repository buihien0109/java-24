package com.example.movieapp.repository;

import com.example.movieapp.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    boolean existsByUser_IdAndMovie_Id(Integer userId, Integer movieId);

    Optional<Favorite> findByUser_IdAndMovie_Id(Integer userId, Integer movieId);

    List<Favorite> findByUser_IdOrderByCreatedAtDesc(Integer id);
}