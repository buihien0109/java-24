package com.example.movieapp;

import com.example.movieapp.entity.*;
import com.example.movieapp.model.enums.MovieType;
import com.example.movieapp.model.enums.UserRole;
import com.example.movieapp.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class MovieAppApplicationTests {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EpisodeRepository episodeRepository;

    @Test
    void save_genres() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();

        for (int i = 0; i < 20; i++) {
            String name = faker.funnyName().name();
            Genre genre = Genre.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            genreRepository.save(genre);
        }
    }

    @Test
    void save_actors() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 100; i++) {
            String name = faker.name().fullName();
            Actor actor = Actor.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .avatar("https://placehold.co/600x400?text=" + name.substring(0, 1).toUpperCase())
                    .bio(faker.lorem().paragraph())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            actorRepository.save(actor);
        }
    }

    @Test
    void save_directors() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        for (int i = 0; i < 30; i++) {
            String name = faker.name().fullName();
            Director director = Director.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .avatar("https://placehold.co/600x400?text=" + name.substring(0, 1).toUpperCase())
                    .bio(faker.lorem().paragraph())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            directorRepository.save(director);
        }
    }

    @Test
    void save_countries() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();

        for (int i = 0; i < 20; i++) {
            String name = faker.country().name();
            Country country = Country.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            countryRepository.save(country);
        }
    }

    @Test
    void save_users() {
        Faker faker = new Faker();

        for (int i = 0; i < 50; i++) {
            String name = faker.name().fullName();
            User user = User.builder()
                    .name(name)
                    .email(faker.internet().emailAddress())
                    .avatar("https://placehold.co/600x400?text=" + name.substring(0, 1).toUpperCase())
                    .password("123")
                    .role(i == 0 || i == 1 ? UserRole.ADMIN : UserRole.USER)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            userRepository.save(user);
        }
    }

    @Test
    void save_movies() {
        Faker faker = new Faker();
        Slugify slugify = Slugify.builder().build();
        Random rd = new Random();

        // Lấy tất cả đối tượng liên quan
        List<Country> countries = countryRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        List<Actor> actors = actorRepository.findAll();
        List<Director> directors = directorRepository.findAll();

        for (int i = 0; i < 100; i++) {
            // Random 1 country
            Country rdCountry = countries.get(rd.nextInt(countries.size()));

            // Random 2 -> 3 genres not duplicate
            List<Genre> rdGenres = new ArrayList<>();
            for (int j = 0; j < rd.nextInt(2) + 1; j++) {
                Genre rdGenre = genres.get(rd.nextInt(genres.size()));
                if (!rdGenres.contains(rdGenre)) {
                    rdGenres.add(rdGenre);
                }
            }

            // Random 5 -> 7 actors not duplicate
            List<Actor> rdActors = new ArrayList<>();
            for (int j = 0; j < rd.nextInt(3) + 5; j++) {
                Actor rdActor = actors.get(rd.nextInt(actors.size()));
                if (!rdActors.contains(rdActor)) {
                    rdActors.add(rdActor);
                }
            }

            // Random 1 -> 3 directors not duplicate
            List<Director> rdDirectors = new ArrayList<>();
            for (int j = 0; j < rd.nextInt(2) + 1; j++) {
                Director rdDirector = directors.get(rd.nextInt(directors.size()));
                if (!rdDirectors.contains(rdDirector)) {
                    rdDirectors.add(rdDirector);
                }
            }

            // Tạo Movie -> Lưu vào trong Database
            String name = faker.book().title();
            Boolean status = faker.bool().bool();
            Movie movie = Movie.builder()
                    .name(name)
                    .slug(slugify.slugify(name))
                    .description(faker.lorem().paragraph())
                    .poster("https://placehold.co/600x400?text=" + name.substring(0, 1).toUpperCase())
                    .releaseYear(faker.number().numberBetween(2021, 2024))
                    .rating(faker.number().randomDouble(1, 1, 10))
                    .trailerUrl("https://www.youtube.com/embed/YPY7J-flzE8?si=NIAaDGXL68JdDCux")
                    .type(MovieType.values()[faker.number().numberBetween(0, MovieType.values().length)])
                    .status(status)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .publishedAt(status ? LocalDateTime.now() : null)
                    .country(rdCountry)
                    .genres(rdGenres)
                    .actors(rdActors)
                    .directors(rdDirectors)
                    .build();
            movieRepository.save(movie);
        }
    }

    @Test
    void save_episodes() {
        Random rd = new Random();
        List<Movie> movies = movieRepository.findAll();

        for (Movie movie : movies) {
            if (movie.getType().equals(MovieType.PHIM_BO)) { // Tạo nhiều tập phim
                // Tạo 5 -> 10 tập phim
                for (int i = 0; i < rd.nextInt(6) + 5; i++) {
                    Episode episode = Episode.builder()
                            .name("Tập " + (i + 1))
                            .duration(50)
                            .videoUrl("https://videos.pexels.com/video-files/3209828/3209828-hd_1280_720_25fps.mp4")
                            .displayOrder(i + 1)
                            .status(true)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .publishedAt(LocalDateTime.now())
                            .movie(movie)
                            .build();
                    episodeRepository.save(episode);
                }
            } else { // Tạo 1 tập phim
                Episode episode = Episode.builder()
                        .name("Tập full")
                        .duration(90)
                        .videoUrl("https://videos.pexels.com/video-files/3209828/3209828-hd_1280_720_25fps.mp4")
                        .displayOrder(1)
                        .status(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .publishedAt(LocalDateTime.now())
                        .movie(movie)
                        .build();
                episodeRepository.save(episode);
            }
        }
    }

    @Test
    void save_reviews() {
    }

    @Test
    void movie_methods_test() {
        // SELECT
        // select * from movies
        List<Movie> movies = movieRepository.findAll();
        System.out.println("movies size = " + movies.size());

        // select * from movies where id in (1, 2, 3)
        List<Movie> moviesByIds = movieRepository.findAllById(List.of(1, 2, 3));

        // select * from movies where id = 1
        Movie movie = movieRepository.findById(1).orElse(null);

        //UPDATE
        movie.setName("Đào, phở, Piano");
        movieRepository.save(movie);

        // DELETE
//        movieRepository.deleteById(2);
//        movieRepository.delete(movie);
//        movieRepository.deleteAll(moviesByIds);
//        movieRepository.deleteAll();
    }
}
