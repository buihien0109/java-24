package com.example.movieapp.service;

import com.example.movieapp.entity.Country;
import com.example.movieapp.entity.Movie;
import com.example.movieapp.exception.ResourceNotFoundException;
import com.example.movieapp.model.request.UpsertMovieRequest;
import com.example.movieapp.repository.*;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll(Sort.by("createdAt").descending());
    }

    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie createMovie(UpsertMovieRequest request) {
        Slugify slugify = Slugify.builder().build();
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Quốc gia không tồn tại"));

        Movie movie = Movie.builder()
                .name(request.getName())
                .slug(slugify.slugify(request.getName()))
                .description(request.getDescription())
                .poster("https://placehold.co/600x400?text=" + request.getName().substring(0, 1).toUpperCase())
                .releaseYear(request.getReleaseYear())
                .trailerUrl(request.getTrailerUrl())
                .type(request.getType())
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .publishedAt(request.getStatus() ? LocalDateTime.now() : null)
                .country(country)
                .genres(genreRepository.findAllById(request.getGenreIds()))
                .actors(actorRepository.findAllById(request.getActorIds()))
                .directors(directorRepository.findAllById(request.getDirectorIds()))
                .build();
        return movieRepository.save(movie);
    }
}
