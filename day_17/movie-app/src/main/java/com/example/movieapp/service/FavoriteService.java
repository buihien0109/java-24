package com.example.movieapp.service;

import com.example.movieapp.entity.Favorite;
import com.example.movieapp.entity.Movie;
import com.example.movieapp.entity.User;
import com.example.movieapp.repository.FavoriteRepository;
import com.example.movieapp.repository.MovieRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final MovieRepository movieRepository;
    private final HttpSession session;

    public Favorite addToFavorite(Integer movieId) {
        // Lấy thông tin user đang đăng nhập
        User user = (User) session.getAttribute("currentUser");

        // Kiểm tra movie có tồn tại không
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        // Kiểm tra movie đã có trong favorite list chưa
        if (favoriteRepository.existsByUser_IdAndMovie_Id(user.getId(), movie.getId())) {
            throw new RuntimeException("Movie already in favorite list");
        }

        // Thêm movie vào favorite list -> lưu vào database
        Favorite favorite = Favorite.builder()
                .user(user)
                .movie(movie)
                .createdAt(LocalDateTime.now())
                .build();
        return favoriteRepository.save(favorite);
    }

    public void deleteFromFavorite(Integer movieId) {
        // Lấy thông tin user đang đăng nhập
        User user = (User) session.getAttribute("currentUser");

        // Kiểm tra movie có tồn tại không
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Optional<Favorite> favoriteOptional = favoriteRepository.findByUser_IdAndMovie_Id(user.getId(), movie.getId());
        if (favoriteOptional.isEmpty()) {
            throw new RuntimeException("Movie not in favorite list");
        }

        favoriteRepository.delete(favoriteOptional.get());
    }

    public boolean isFavorite(Integer movieId) {
        // Lấy thông tin user đang đăng nhập
        User user = (User) session.getAttribute("currentUser");

        // Kiểm tra movie có tồn tại không
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        return favoriteRepository.existsByUser_IdAndMovie_Id(user.getId(), movie.getId());
    }

    public List<Favorite> getAllFavoritesByCurrentUser() {
        // Lấy thông tin user đang đăng nhập
        User user = (User) session.getAttribute("currentUser");
        return favoriteRepository.findByUser_IdOrderByCreatedAtDesc(user.getId());
    }
}
