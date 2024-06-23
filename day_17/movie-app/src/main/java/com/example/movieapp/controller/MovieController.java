package com.example.movieapp.controller;

import com.example.movieapp.entity.Movie;
import com.example.movieapp.repository.ActorRepository;
import com.example.movieapp.repository.CountryRepository;
import com.example.movieapp.repository.DirectorRepository;
import com.example.movieapp.repository.GenreRepository;
import com.example.movieapp.service.MovieService;
import com.example.movieapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final GenreRepository genreRepository;
    private final CountryRepository countryRepository;

    @GetMapping
    public String getIndex(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin/movie/index";
    }

    @GetMapping("/create")
    public String getCreate(Model model) {
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("countries", countryRepository.findAll());
        return "admin/movie/create";
    }

    @GetMapping("/{id}/detail")
    public String getDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("user", movieService.getMovieById(id));
        model.addAttribute("directors", directorRepository.findAll());
        model.addAttribute("actors", actorRepository.findAll());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("countries", countryRepository.findAll());
        return "admin/movie/detail";
    }
}
