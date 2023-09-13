package com.reactivespring.service;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService {

    private MovieInfoRepository movieInfoRepository;

    public MovieInfoService(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo) {
        return movieInfoRepository.save(movieInfo);
    }

    public Flux<MovieInfo> getAllMovies() {
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> getMovieById(String movieId) {
        return movieInfoRepository.findById(movieId);
    }

    public Mono<MovieInfo> updateMovieById(String movieId, MovieInfo newMovieInfo) {
        return movieInfoRepository.findById(movieId)
                .flatMap(movieInfo -> {
                    movieInfo.setName(newMovieInfo.getName());
                    movieInfo.setCasts(newMovieInfo.getCasts());
                    movieInfo.setYear(newMovieInfo.getYear());
                    movieInfo.setReleaseDate(newMovieInfo.getReleaseDate());
                    return movieInfoRepository.save(movieInfo);
                });
    }

    public Mono<Void> deleteMovieById(String movieId) {
        return movieInfoRepository.deleteById(movieId);
    }
}
