package com.example.movieapp.controller;

import com.example.movieapp.entity.*;
import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.service.EpisodeService;
import com.example.movieapp.service.FavoriteService;
import com.example.movieapp.service.ReviewService;
import com.example.movieapp.service.WebService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {
    private final WebService webService;
    private final EpisodeService episodeService;
    private final ReviewService reviewService;
    private final FavoriteService favoriteService;
    private final HttpSession session;

    @GetMapping
    public String getHomePage(Model model) {
        List<Movie> dsPhimHot = webService.getHotMovie();
        List<Movie> dsPhimBo = webService.findByType(MovieType.PHIM_BO, true, 1, 6).getContent();
        List<Movie> dsPhimLe = webService.findByType(MovieType.PHIM_LE, true, 1, 6).getContent();
        List<Movie> dsPhimChieuRap = webService.findByType(MovieType.PHIM_CHIEU_RAP, true, 1, 6).getContent();

        model.addAttribute("dsPhimHot", dsPhimHot);
        model.addAttribute("dsPhimBo", dsPhimBo);
        model.addAttribute("dsPhimLe", dsPhimLe);
        model.addAttribute("dsPhimChieuRap", dsPhimChieuRap);
        return "web/index";
    }

    // http://localhost:8080/phim-bo
    // http://localhost:8080/phim-bo?page=1&limit=12
    @GetMapping("/phim-bo")
    public String getPhimBoPage(Model model,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "12") Integer limit) {
        Page<Movie> pageData = webService.findByType(MovieType.PHIM_BO, true, page, limit);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "web/phim-bo";
    }

    @GetMapping("/phim-le")
    public String getPhimLePage(Model model,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "12") Integer limit) {
        Page<Movie> pageData = webService.findByType(MovieType.PHIM_LE, true, page, limit);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "web/phim-le";
    }

    @GetMapping("/phim-chieu-rap")
    public String getPhimChieuRapPage(Model model,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "12") Integer limit) {
        Page<Movie> pageData = webService.findByType(MovieType.PHIM_CHIEU_RAP, true, page, limit);
        model.addAttribute("pageData", pageData);
        model.addAttribute("currentPage", page);
        return "web/phim-chieu-rap";
    }

    @GetMapping("/phim/{id}/{slug}")
    public String getMovieDetailsPage(@PathVariable Integer id,
                                      @PathVariable String slug,
                                      Model model) {
        User user = (User) session.getAttribute("currentUser");
        // Trả về thông tin phim
        Movie movie = webService.getMovieDetails(id, slug);
        // Trả về danh sách phim liên quan
        List<Movie> relatedMovies = webService.getRelatedMovies(movie);
        // Trả về danh sách tập phim
        List<Episode> episodes = episodeService.getEpisodeListOfMovie(id);
        // Trả về danh sách review
        List<Review> reviews = reviewService.getReviewsByMovie(id);

        if (user != null) {
            boolean isFavorite = favoriteService.isFavorite(id);
            model.addAttribute("isFavorite", isFavorite);
        }

        model.addAttribute("movie", movie);
        model.addAttribute("relatedMovies", relatedMovies);
        model.addAttribute("reviews", reviews);
        model.addAttribute("episodes", episodes);
        return "web/chi-tiet-phim";
    }

    // http://localhost:8080/xem-phim/99/the-torment-of-others?tap=full
    // http://localhost:8080/xem-phim/99/the-torment-of-others?tap=1
    @GetMapping("/xem-phim/{id}/{slug}")
    public String getMovieStreamingDetailsPage(@PathVariable Integer id,
                                               @PathVariable String slug,
                                               @RequestParam String tap,
                                               Model model) {
        // Trả về thông tin phim
        Movie movie = webService.getMovieDetails(id, slug);

        // Trả về danh sách phim liên quan
        List<Movie> relatedMovies = webService.getRelatedMovies(movie);

        // Trả về danh sách tập phim
        List<Episode> episodes = episodeService.getEpisodeListOfMovie(id);

        // Trả về danh sách review
        List<Review> reviews = reviewService.getReviewsByMovie(id);

        // Lấy ra thông tin tập phim cần xem (tap : displayOrder)
        Episode currentEpisode = episodeService.getEpisodeByDisplayOrder(id, tap);

        model.addAttribute("movie", movie);
        model.addAttribute("relatedMovies", relatedMovies);
        model.addAttribute("reviews", reviews);
        model.addAttribute("episodes", episodes);
        model.addAttribute("currentEpisode", currentEpisode);
        return "web/xem-phim";
    }

    @GetMapping("/phim-yeu-thich")
    public String getFavoritePage(Model model) {
        List<Favorite> favorites = favoriteService.getAllFavoritesByCurrentUser();
        model.addAttribute("favorites", favorites);
        return "web/phim-yeu-thich";
    }

    @GetMapping("/dang-nhap")
    public String getLoginPage() {
        return "web/dang-nhap";
    }

    @GetMapping("/thong-tin-ca-nhan")
    public String getProfilePage(Model model) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        return "web/thong-tin-ca-nhan";
    }
}
