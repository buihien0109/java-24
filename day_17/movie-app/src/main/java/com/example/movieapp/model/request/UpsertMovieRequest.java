package com.example.movieapp.model.request;

import com.example.movieapp.model.enums.MovieType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertMovieRequest {
    @NotEmpty(message = "Tên phim không được để trống")
    String name;

    @NotEmpty(message = "Trailer không được để trống")
    String trailerUrl;

    @NotEmpty(message = "Mô tả không được để trống")
    String description;

    List<Integer> genreIds;
    List<Integer> actorIds;
    List<Integer> directorIds;

    @NotNull(message = "Năm phát hành không được để trống")
    Integer releaseYear;

    @NotNull(message = "Loại phim không được để trống")
    MovieType type;

    @NotNull(message = "Trạng thái không được để trống")
    Boolean status;

    @NotNull(message = "Quốc gia không được để trống")
    Integer countryId;
}
