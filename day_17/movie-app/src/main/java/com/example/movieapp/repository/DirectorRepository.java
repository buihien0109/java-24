package com.example.movieapp.repository;

import com.example.movieapp.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
}